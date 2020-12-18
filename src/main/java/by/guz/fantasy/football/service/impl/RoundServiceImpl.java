package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.RoundDto;
import by.guz.fantasy.football.repository.RoundRepository;
import by.guz.fantasy.football.service.RoundService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.mapper.RoundMapper.ROUND_MAPPER;

@Service
@AllArgsConstructor
public class RoundServiceImpl implements RoundService {

    private final RoundRepository roundRepository;

    @Override
    public List<RoundDto.Response.Default> getAllRounds() {
        return roundRepository.findAll()
                .stream()
                .map(ROUND_MAPPER::toRoundDtoDefault)
                .collect(Collectors.toList());
    }
}
