package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.LineupDto;
import by.guz.fantasy.football.exception.NotFoundException;
import by.guz.fantasy.football.repository.LineupRepository;
import by.guz.fantasy.football.repository.UserRepository;
import by.guz.fantasy.football.service.LineupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.exception.Constants.USER_NOT_FOUND;
import static by.guz.fantasy.football.mapper.LineupMapper.LINEUP_MAPPER;
import static by.guz.fantasy.football.util.SecurityUtil.getUserId;

@Service
@AllArgsConstructor
public class LineupServiceImpl implements LineupService {

    private final LineupRepository lineupRepository;
    private final UserRepository userRepository;

    @Override
    public List<LineupDto.Response.Default> getCurrentUserLineups() {
        userExistsOrException(getUserId());
        return lineupRepository.findAllByUserId(getUserId())
                .stream()
                .map(LINEUP_MAPPER::toLineupDtoDefault)
                .collect(Collectors.toList());
    }

    private void userExistsOrException(final Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }
}
