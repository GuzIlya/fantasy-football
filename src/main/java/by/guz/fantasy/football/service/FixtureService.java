package by.guz.fantasy.football.service;

import by.guz.fantasy.football.dto.FixtureDto;

import java.util.List;

public interface FixtureService {

    List<FixtureDto.Response.Default> getAllFixtures();
    FixtureDto.Response.Default getFixtureById(Long fixtureId);
}
