package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.configuration.AppConfiguration;
import by.guz.fantasy.football.dto.*;
import by.guz.fantasy.football.entity.*;
import by.guz.fantasy.football.entity.enums.FixtureStatusEntity;
import by.guz.fantasy.football.entity.enums.PlayerPositionEntity;
import by.guz.fantasy.football.entity.enums.RoundStatusEntity;
import by.guz.fantasy.football.exception.ConflictException;
import by.guz.fantasy.football.exception.NotFoundException;
import by.guz.fantasy.football.repository.*;
import by.guz.fantasy.football.repository.custom.CustomPlayerRepository;
import by.guz.fantasy.football.service.AdminService;
import by.guz.fantasy.football.service.FootballApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static by.guz.fantasy.football.exception.Constants.*;
import static by.guz.fantasy.football.mapper.FixtureMapper.FIXTURE_MAPPER;
import static by.guz.fantasy.football.mapper.PlayerMapper.PLAYER_MAPPER;
import static by.guz.fantasy.football.mapper.PlayerStatisticsMapper.PLAYER_STATISTICS_MAPPER;
import static by.guz.fantasy.football.mapper.TeamMapper.TEAM_MAPPER;

@Service
@AllArgsConstructor
@Log4j2
public class AdminServiceImpl implements AdminService {

    private final FootballApiService footballApiService;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final RoundRepository roundRepository;
    private final FixtureRepository fixtureRepository;
    private final PlayerStatisticsRepository playerStatisticsRepository;
    private final AppConfiguration appConfiguration;

    private final CustomPlayerRepository customPlayerRepository;

    @Override
    @Transactional
    public void updatePlayers() throws JsonProcessingException {
        if (!appConfiguration.getApiFootball().isEnable())
            throw new ConflictException(EXTERNAL_API_UNABLE_CONFLICT);

        Integer page = 1;

        while (true) {
            JsonNode node = footballApiService.getPlayersWithPagination(page);

            PlayerDto.External.DefaultList players = new ObjectMapper().treeToValue(node, PlayerDto.External.DefaultList.class);

            if (players == null || players.getResponse().isEmpty())
                break;

            for (PlayerDto.External.DefaultCover player : players.getResponse()) {
                Optional<PlayerEntity> existing = customPlayerRepository.getOneByExternalId(player.getPlayer().getId());

                PlayerEntity playerToSave;

                if (existing.isPresent()) {
                    playerToSave = PLAYER_MAPPER.updateEntity(player.getPlayer(), existing.get());
                } else {
                    playerToSave = PLAYER_MAPPER.toPlayerEntity(player.getPlayer());
                    playerToSave.setCost(appConfiguration.getGame().getDefaultCost());
                }

                playerToSave.setPosition(PlayerPositionEntity.fromValue(player.getStatistics().get(0).getGames().getPosition().toUpperCase()));
                Optional<TeamEntity> playersTeam = teamRepository.findOneByExternalId(player.getStatistics().get(0).getTeam().getId());
                playersTeam.ifPresent(playerToSave::setTeam);
                playerRepository.saveAndFlush(playerToSave);
            }

            page++;
        }
    }

    @Override
    @Transactional
    public void updateTeams() throws JsonProcessingException {
        if (!appConfiguration.getApiFootball().isEnable())
            throw new ConflictException(EXTERNAL_API_UNABLE_CONFLICT);

        JsonNode node = footballApiService.getTeams();

        TeamDto.External.DefaultList teams = new ObjectMapper().treeToValue(node, TeamDto.External.DefaultList.class);
        if (teams != null && !teams.getResponse().isEmpty())
            for (TeamDto.External.DefaultCover team : teams.getResponse()) {
                Optional<TeamEntity> existing = teamRepository.findOneByExternalId(team.getTeam().getId());
                if (existing.isPresent()) {
                    TeamEntity updated = TEAM_MAPPER.updateEntity(team.getTeam(), existing.get());
                    teamRepository.saveAndFlush(updated);
                } else {
                    teamRepository.saveAndFlush(TEAM_MAPPER.toTeamEntity(team.getTeam()));
                }
            }
    }

    @Override
    @Transactional
    public void updateRounds() throws JsonProcessingException {
        if (!appConfiguration.getApiFootball().isEnable())
            throw new ConflictException(EXTERNAL_API_UNABLE_CONFLICT);

        JsonNode node = footballApiService.getRounds();

        RoundDto.External.Default rounds = new ObjectMapper().treeToValue(node, RoundDto.External.Default.class);

        for (String name : rounds.getResponse()) {
            if (!roundRepository.existsByName(name))
                roundRepository.saveAndFlush(RoundEntity.builder()
                        .name(name)
                        .status(RoundStatusEntity.UPCOMING)
                        .build());
        }
    }

    @Override
    @Transactional
    public void updateFixtures() throws JsonProcessingException {
        if (!appConfiguration.getApiFootball().isEnable())
            throw new ConflictException(EXTERNAL_API_UNABLE_CONFLICT);

        JsonNode node = footballApiService.getFixtures();

        FixtureDto.External.DefaultList fixtures = new ObjectMapper().treeToValue(node, FixtureDto.External.DefaultList.class);

        if (fixtures != null && !fixtures.getResponse().isEmpty())
            for (FixtureDto.External.Default fixture : fixtures.getResponse()) {
                Optional<FixtureEntity> existing = fixtureRepository.findOneByExternalId(fixture.getFixture().getId());
                FixtureEntity fixtureToSave;

                if (existing.isPresent()) {
                    fixtureToSave = FIXTURE_MAPPER.updateEntity(fixture, existing.get());
                } else {
                    fixtureToSave = FIXTURE_MAPPER.toFixtureEntity(fixture);

                    TeamEntity home = teamRepository.findOneByExternalId(fixture.getTeams().getHome().getId())
                            .orElseThrow(() -> new NotFoundException(TEAM_NOT_FOUND));

                    fixtureToSave.setAwayTeam(TeamEntity.builder().id(home.getId()).build());

                    TeamEntity away = teamRepository.findOneByExternalId(fixture.getTeams().getAway().getId())
                            .orElseThrow(() -> new NotFoundException(TEAM_NOT_FOUND));

                    fixtureToSave.setHomeTeam(TeamEntity.builder().id(away.getId()).build());

                    RoundEntity round = roundRepository.findOneByName(fixture.getLeague().getRound())
                            .orElseThrow(() -> new NotFoundException(ROUND_NOT_FOUND));

                    fixtureToSave.setRound(RoundEntity.builder().id(round.getId()).build());
                }

                OffsetDateTime timed = OffsetDateTime.parse(fixture.getFixture().getDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                fixtureToSave.setDate(Instant.from(timed));
                fixtureToSave.setStatus(FixtureStatusEntity.fromValue(fixture.getFixture().getStatus().getStatus()));
                fixtureRepository.saveAndFlush(fixtureToSave);

            }
    }

    @Override
    @Transactional
    public void updatePlayerStatistics(Long fixtureId) throws JsonProcessingException {
        if (!appConfiguration.getApiFootball().isEnable())
            throw new ConflictException(EXTERNAL_API_UNABLE_CONFLICT);

        FixtureEntity fixture = fixtureRepository.findOneById(fixtureId)
                .orElseThrow(() -> new NotFoundException(FIXTURE_NOT_FOUND));

        playerStatisticsRepository.deleteAllByFixtureId(fixtureId);

        JsonNode node = footballApiService.getFixtureById(fixture.getExternalId());

        PlayerStatisticsDto.External.DefaultList statisticsList = new ObjectMapper().treeToValue(node, PlayerStatisticsDto.External.DefaultList.class);

        if (statisticsList != null && !statisticsList.getResponse().isEmpty())
            for (PlayerStatisticsDto.External.DefaultTeamList teamInfo : statisticsList.getResponse()) {
                for (PlayerStatisticsDto.External.Default teamPlayersInfo : teamInfo.getPlayers()) {
                    for (PlayerStatisticsDto.External.Players playerInfo : teamPlayersInfo.getPlayers()) {
                        PlayerStatisticsEntity statistics = PLAYER_STATISTICS_MAPPER.toPlayerStatisticsEntity(playerInfo.getStatistics().get(0));

                        statistics.setFixture(fixture);

                        PlayerEntity player = playerRepository.findOneByExternalId(playerInfo.getPlayer().getId())
                                .orElseThrow(() -> new NotFoundException(PLAYER_NOT_FOUND));

                        statistics.setPlayer(player);

                        playerStatisticsRepository.saveAndFlush(statistics);
                    }
                }
            }
    }
}
