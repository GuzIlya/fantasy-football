package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.configuration.AppConfiguration;
import by.guz.fantasy.football.dto.PlayerDto;
import by.guz.fantasy.football.dto.RoundDto;
import by.guz.fantasy.football.dto.TeamDto;
import by.guz.fantasy.football.entity.PlayerEntity;
import by.guz.fantasy.football.entity.RoundEntity;
import by.guz.fantasy.football.entity.TeamEntity;
import by.guz.fantasy.football.entity.enums.RoundStatusEntity;
import by.guz.fantasy.football.exception.ConflictException;
import by.guz.fantasy.football.repository.PlayerRepository;
import by.guz.fantasy.football.repository.RoundRepository;
import by.guz.fantasy.football.repository.TeamRepository;
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

import java.util.Optional;

import static by.guz.fantasy.football.exception.Constants.EXTERNAL_API_UNABLE_CONFLICT;
import static by.guz.fantasy.football.mapper.PlayerMapper.PLAYER_MAPPER;
import static by.guz.fantasy.football.mapper.TeamMapper.TEAM_MAPPER;

@Service
@AllArgsConstructor
@Log4j2
public class AdminServiceImpl implements AdminService {

    private final FootballApiService footballApiService;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final RoundRepository roundRepository;
    private final AppConfiguration appConfiguration;

    private final CustomPlayerRepository customPlayerRepository;

    @Override
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
}
