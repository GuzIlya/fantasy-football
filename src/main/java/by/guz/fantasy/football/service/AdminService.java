package by.guz.fantasy.football.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface AdminService {

    void updatePlayers() throws JsonProcessingException;

    void updateTeams() throws JsonProcessingException;

    void updateRounds() throws JsonProcessingException;

}
