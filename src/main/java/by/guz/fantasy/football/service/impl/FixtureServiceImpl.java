package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.FixtureDto;
import by.guz.fantasy.football.entity.FixtureEntity;
import by.guz.fantasy.football.exception.NotFoundException;
import by.guz.fantasy.football.repository.FixtureRepository;
import by.guz.fantasy.football.service.FixtureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.exception.Constants.FIXTURE_NOT_FOUND;
import static by.guz.fantasy.football.mapper.FixtureMapper.FIXTURE_MAPPER;

@Service
@AllArgsConstructor
public class FixtureServiceImpl implements FixtureService {

    private final FixtureRepository fixtureRepository;

    @Override
    public List<FixtureDto.Response.Default> getAllFixtures() {
        return fixtureRepository.findAll()
                .stream()
                .map(FIXTURE_MAPPER::toFixtureDtoDefault)
                .collect(Collectors.toList());
    }

    @Override
    public FixtureDto.Response.Default getFixtureById(Long fixtureId) {
        FixtureEntity existing = fixtureRepository.findOneById(fixtureId)
                .orElseThrow(() -> new NotFoundException(FIXTURE_NOT_FOUND));
        return FIXTURE_MAPPER.toFixtureDtoDefault(existing);
    }
}
