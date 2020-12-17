package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.TeamDto;
import by.guz.fantasy.football.repository.TeamRepository;
import by.guz.fantasy.football.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.mapper.TeamMapper.TEAM_MAPPER;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public List<TeamDto.Response.Default> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(TEAM_MAPPER::toTeamDtoDefault)
                .collect(Collectors.toList());
    }
}
