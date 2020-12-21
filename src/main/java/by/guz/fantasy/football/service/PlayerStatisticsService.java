package by.guz.fantasy.football.service;

import by.guz.fantasy.football.dto.PlayerStatisticsDto;

import java.util.List;

public interface PlayerStatisticsService {

    List<PlayerStatisticsDto.Response.Default> getPlayerStatisticsByFixtureId(Long fixtureId);
}
