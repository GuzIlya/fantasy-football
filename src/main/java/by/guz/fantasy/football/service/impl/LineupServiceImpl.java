package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.LineupDto;
import by.guz.fantasy.football.dto.LineupPlayerDto;
import by.guz.fantasy.football.entity.*;
import by.guz.fantasy.football.entity.enums.RoundStatusEntity;
import by.guz.fantasy.football.exception.ConflictException;
import by.guz.fantasy.football.exception.NotFoundException;
import by.guz.fantasy.football.repository.*;
import by.guz.fantasy.football.service.LineupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.exception.Constants.*;
import static by.guz.fantasy.football.mapper.LineupMapper.LINEUP_MAPPER;
import static by.guz.fantasy.football.mapper.LineupPlayerMapper.LINEUP_PLAYER_MAPPER;
import static by.guz.fantasy.football.util.SecurityUtil.getUserId;

@Service
@AllArgsConstructor
public class LineupServiceImpl implements LineupService {

    private final LineupRepository lineupRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final RoundRepository roundRepository;
    private final LineupPlayerRepository lineupPlayerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LineupDto.Response.Default> getCurrentUserLineups() {
        userExistsOrException(getUserId());
        List<LineupDto.Response.Default> lineups = lineupRepository.findAllByUserId(getUserId())
                .stream()
                .map(LINEUP_MAPPER::toLineupDtoDefault)
                .collect(Collectors.toList());

        for (LineupDto.Response.Default lineup : lineups) {
            lineup.setLineup(lineupPlayerRepository.findAllByLineupId(lineup.getId())
                    .stream()
                    .map(LINEUP_PLAYER_MAPPER::toLineupPlayerDtoExtended)
                    .collect(Collectors.toList()));
        }

        return lineups;
    }

    @Override
    @Transactional
    public void setLineupToCurrentUser(LineupDto.Request.Default dto) {
        userExistsOrException(getUserId());

        RoundEntity round = roundRepository.findById(dto.getRoundId())
                .orElseThrow(() -> new NotFoundException(ROUND_NOT_FOUND));

        if (round.getStatus() != RoundStatusEntity.OPEN) {
            throw new ConflictException(ROUND_NOT_OPEN_CONFLICT);
        }

        Optional<LineupEntity> lineupCandidate = lineupRepository.findByUserIdAndRoundId(getUserId(), dto.getRoundId());

        LineupEntity lineup;
        if (lineupCandidate.isEmpty()) {
            lineup = LineupEntity.builder()
                    .round(RoundEntity.builder().id(dto.getRoundId()).build())
                    .user(UserEntity.builder().id(getUserId()).build())
                    .build();
            lineupRepository.saveAndFlush(lineup);
        } else {
            lineup = lineupCandidate.get();
            lineup.setUpdatedAt(Instant.now());
        }

        lineupPlayerRepository.deleteAllByLineupId(lineup.getId());

        for (LineupPlayerDto.Default item : dto.getLineup()) {
            playerExistsOrException(item.getPlayerId());

            if (!playerRepository.existsByUserIdAndId(getUserId(), item.getPlayerId())) {
                throw new NotFoundException(PLAYER_DONT_PURCHASED_CONFLICT + item.getPlayerId());
            }

            lineupPlayerRepository.saveAndFlush(LineupPlayerEntity.builder()
                    .number(item.getNumber())
                    .lineup(lineup)
                    .player(PlayerEntity.builder().id(item.getPlayerId()).build())
                    .build());
        }
    }

    private void userExistsOrException(final Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    private void playerExistsOrException(final Long playerId) {
        if (!playerRepository.existsById(playerId)) {
            throw new NotFoundException(PLAYER_NOT_FOUND);
        }
    }
}
