package by.guz.fantasy.football.service;

import by.guz.fantasy.football.dto.TeamDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface AdminService {

    JsonNode updatePlayers();

    List<TeamDto.Response.Default> updateTeams() throws JsonProcessingException;

}
