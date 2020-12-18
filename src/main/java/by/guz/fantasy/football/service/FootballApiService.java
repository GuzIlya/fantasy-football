package by.guz.fantasy.football.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface FootballApiService {

    JsonNode getPlayers();

    JsonNode getPlayersWithPagination(Integer page);

    JsonNode getTeams();

    JsonNode getRounds();

}
