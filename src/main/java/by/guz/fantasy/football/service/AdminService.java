package by.guz.fantasy.football.service;

import by.guz.fantasy.football.dto.PlayerDto;
import by.guz.fantasy.football.dto.TeamDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface AdminService {

    List<PlayerDto.Response.Default> updatePlayers() throws JsonProcessingException;

    List<TeamDto.Response.Default> updateTeams() throws JsonProcessingException;

}
