package by.guz.fantasy.football.service;

import java.util.Map;

public interface PlayerService {

    Map<String, Object> getAllPlayers(int page, int size);

    Map<String, Object> getAllPlayersFounded(int page, int size, String search, Integer minAge, Integer maxAge,
                                             String nationality, Double minCost, Double maxCost, Long teamId);
}
