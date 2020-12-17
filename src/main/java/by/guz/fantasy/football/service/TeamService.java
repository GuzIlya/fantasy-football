package by.guz.fantasy.football.service;

import by.guz.fantasy.football.dto.TeamDto;

import java.util.List;

public interface TeamService {

    List<TeamDto.Response.Default> getAllTeams();
}
