package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.service.FootballApiService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class FootballApiServiceImpl implements FootballApiService {

    private final WebClient webClient;

    @Override
    public JsonNode getPlayers() {
        return webClient
                .get()
                .uri("players?league=39&season=2020")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    @Override
    public JsonNode getPlayersWithPagination(Integer page) {
        return webClient
                .get()
                .uri("players?league=39&season=2020&page=" + page)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    @Override
    public JsonNode getTeams() {
        return webClient
                .get()
                .uri("teams?league=39&season=2020")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    @Override
    public JsonNode getRounds() {
        return webClient
                .get()
                .uri("fixtures/rounds?league=39&season=2020")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    @Override
    public JsonNode getFixtures() {
        return webClient
                .get()
                .uri("fixtures?league=39&season=2020")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }


    @Override
    public JsonNode getFixtureById(Long id) {
        return webClient
                .get()
                .uri("fixtures?id=" + id)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
