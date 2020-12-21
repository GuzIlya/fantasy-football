package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.PlayerStatisticsDto;
import by.guz.fantasy.football.repository.PlayerStatisticsRepository;
import by.guz.fantasy.football.service.PlayerStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.mapper.PlayerStatisticsMapper.PLAYER_STATISTICS_MAPPER;

@Service
@AllArgsConstructor
public class PlayerStatisticsServiceImpl implements PlayerStatisticsService {

    private final PlayerStatisticsRepository playerStatisticsRepository;

    @Override
    public List<PlayerStatisticsDto.Response.Default> getPlayerStatisticsByFixtureId(Long fixtureId) {
        return playerStatisticsRepository.findAllByFixtureId(fixtureId)
                .stream()
                .map(PLAYER_STATISTICS_MAPPER::toPlayerStatisticsDefault)
                .collect(Collectors.toList());
    }
}
