package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.TeamDto;
import by.guz.fantasy.football.entity.TeamEntity;
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

import static by.guz.fantasy.football.mapper.TeamMapper.TEAM_MAPPER;

@Service
@AllArgsConstructor
@Log4j2
public class AdminServiceImpl implements AdminService {

    private final FootballApiService footballApiService;
    private final TeamRepository teamRepository;

    @Override
    public JsonNode updatePlayers() {
        return footballApiService.getPlayers().get("response");
    }

    @Override
    @Transactional
    public List<TeamDto.Response.Default> updateTeams() throws JsonProcessingException {
        JsonNode node = footballApiService.getTeams();

        TeamDto.External.DefaultList teams = new ObjectMapper().treeToValue(node, TeamDto.External.DefaultList.class);
        if (teams != null && teams.getResponse() != null)
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
