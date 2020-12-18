package by.guz.fantasy.football.service;

import by.guz.fantasy.football.dto.PlayerDto;
import by.guz.fantasy.football.entity.enums.PlayerPositionEntity;

import java.util.List;
import java.util.Map;

public interface PlayerService {

    Map<String, Object> getAllPlayers(int page, int size);

    Map<String, Object> getAllPlayersFounded(int page, int size, String search, Integer minAge, Integer maxAge,
                                             String nationality, Double minCost, Double maxCost, Long teamId, PlayerPositionEntity position);

    List<PlayerDto.Response.Default> getCurrentUserPlayers();
}
