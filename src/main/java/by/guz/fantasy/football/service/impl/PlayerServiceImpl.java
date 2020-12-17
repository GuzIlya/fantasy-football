package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.PageDto;
import by.guz.fantasy.football.entity.PlayerEntity;
import by.guz.fantasy.football.entity.PlayerEntity_;
import by.guz.fantasy.football.repository.PlayerRepository;
import by.guz.fantasy.football.repository.custom.CustomPlayerRepository;
import by.guz.fantasy.football.repository.custom.Filter;
import by.guz.fantasy.football.repository.custom.QueryOperator;
import by.guz.fantasy.football.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.mapper.PlayerMapper.PLAYER_MAPPER;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final CustomPlayerRepository customPlayerRepository;

    @Override
    public Map<String, Object> getAllPlayers(int page, int size) {
        Map<String, Object> response = new HashMap<>();

        Page<PlayerEntity> playersPage = playerRepository.findAll(PageRequest.of(page, size));

        response.put("paging", new PageDto(playersPage.getNumber(), playersPage.getTotalPages()));
        response.put("players", playersPage.getContent()
                .stream()
                .map(PLAYER_MAPPER::toPlayerDtoDefault)
                .collect(Collectors.toList()));

        return response;
    }

    @Override
    public Map<String, Object> getAllPlayersFounded(int page, int size, String search, Integer minAge, Integer maxAge,
                                                    String nationality, Double minCost, Double maxCost, Long teamId) {
        Map<String, Object> response = new HashMap<>();

        List<Filter> filters = new ArrayList<>();

        if (search != null) {
            filters.add(Filter.builder()
                    .fields(List.of(PlayerEntity_.NAME, PlayerEntity_.FIRSTNAME, PlayerEntity_.LASTNAME))
                    .operator(QueryOperator.TRIPLE_OR_LIKE)
                    .value(search)
                    .build());
        }
        if (minAge != null) {
            filters.add(Filter.builder()
                    .field(PlayerEntity_.BIRTHDATE)
                    .operator(QueryOperator.GREATER_THAN_AGE)
                    .value(minAge.toString())
                    .build());
        }

        if (maxAge != null) {
            filters.add(Filter.builder()
                    .field(PlayerEntity_.BIRTHDATE)
                    .operator(QueryOperator.LESS_THAN_AGE)
                    .value(maxAge.toString())
                    .build());
        }

        if (nationality != null) {
            filters.add(Filter.builder()
                    .field(PlayerEntity_.NATIONALITY)
                    .operator(QueryOperator.EQUALS)
                    .value(nationality)
                    .build());
        }

        if (minCost != null) {
            filters.add(Filter.builder()
                    .field(PlayerEntity_.COST)
                    .operator(QueryOperator.GREATER_THAN)
                    .value(minCost.toString())
                    .build());
        }

        if (maxCost != null) {
            filters.add(Filter.builder()
                    .field(PlayerEntity_.COST)
                    .operator(QueryOperator.LESS_THAN)
                    .value(maxCost.toString())
                    .build());
        }

        if (teamId != null) {
            filters.add(Filter.builder()
                    .field(PlayerEntity_.TEAM_ID)
                    .operator(QueryOperator.EQUALS)
                    .value(teamId.toString())
                    .build());
        }

        Page<PlayerEntity> playersPage = customPlayerRepository.getQueryResult(filters, PageRequest.of(page - 1, size));

        response.put("paging", new PageDto(playersPage.getNumber() + 1, playersPage.getTotalPages()));
        response.put("players", playersPage.getContent()
                .stream()
                .map(PLAYER_MAPPER::toPlayerDtoDefault)
                .collect(Collectors.toList()));

        return response;
    }
}
