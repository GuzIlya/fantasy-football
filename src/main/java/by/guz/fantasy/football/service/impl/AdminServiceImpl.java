package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.configuration.AppConfiguration;
import by.guz.fantasy.football.dto.PlayerDto;
import by.guz.fantasy.football.dto.TeamDto;
import by.guz.fantasy.football.entity.PlayerEntity;
import by.guz.fantasy.football.entity.TeamEntity;
import by.guz.fantasy.football.exception.ConflictException;
import by.guz.fantasy.football.repository.PlayerRepository;
import by.guz.fantasy.football.repository.TeamRepository;
import by.guz.fantasy.football.service.AdminService;
import by.guz.fantasy.football.service.FootballApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final AppConfiguration appConfiguration;

    @Override
    public List<PlayerDto.Response.Default> updatePlayers() throws JsonProcessingException {
        if (!appConfiguration.getApiFootball().isEnable())
            throw new ConflictException(EXTERNAL_API_UNABLE_CONFLICT);

        Integer page = 1;

        while (true) {
            JsonNode node = footballApiService.getPlayersWithPagination(page);

            PlayerDto.External.DefaultList players = new ObjectMapper().treeToValue(node, PlayerDto.External.DefaultList.class);

            if (players == null || players.getResponse().isEmpty())
                break;

            for (PlayerDto.External.DefaultCover player : players.getResponse()) {
                Optional<PlayerEntity> existing = playerRepository.findOneByExternalId(player.getPlayer().getId());

                PlayerEntity playerToSave;

                if (existing.isPresent()) {
                    playerToSave = PLAYER_MAPPER.updateEntity(player.getPlayer(), existing.get());
                } else {
                    playerToSave = PLAYER_MAPPER.toTeamEntity(player.getPlayer());
                    playerToSave.setCost(appConfiguration.getGame().getDefaultCost());
                }

                Optional<TeamEntity> playersTeam = teamRepository.findOneByExternalId(player.getStatistics().get(0).getTeam().getId());
                playersTeam.ifPresent(playerToSave::setTeam);
                playerRepository.saveAndFlush(playerToSave);
            }

            page++;
        }

        return playerRepository.findAll()
                .stream()
                .map(PLAYER_MAPPER::toTeamDtoDefault)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TeamDto.Response.Default> updateTeams() throws JsonProcessingException {
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

        return teamRepository.findAll()
                .stream()
                .map(TEAM_MAPPER::toTeamDtoDefault)
                .collect(Collectors.toList());
    }
}
