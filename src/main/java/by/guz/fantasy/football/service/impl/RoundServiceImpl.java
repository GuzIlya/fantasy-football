package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.RoundDto;
import by.guz.fantasy.football.entity.*;
import by.guz.fantasy.football.entity.enums.RoundStatusEntity;
import by.guz.fantasy.football.exception.ConflictException;
import by.guz.fantasy.football.exception.NotFoundException;
import by.guz.fantasy.football.repository.*;
import by.guz.fantasy.football.service.RoundService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.exception.Constants.ROUND_NOT_FOUND;
import static by.guz.fantasy.football.exception.Constants.ROUND_STATUS_CONFLICT;
import static by.guz.fantasy.football.mapper.RoundMapper.ROUND_MAPPER;

@Service
@AllArgsConstructor
public class RoundServiceImpl implements RoundService {

    private final RoundRepository roundRepository;
    private final LineupRepository lineupRepository;
    private final PlayerRepository playerRepository;
    private final PlayerStatisticsRepository playerStatisticsRepository;
    private final UserStatisticsRepository userStatisticsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoundDto.Response.Default> getAllRounds() {
        return roundRepository.findAll()
                .stream()
                .map(ROUND_MAPPER::toRoundDtoDefault)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoundDto.Response.Default getRoundById(Long roundId) {
        RoundEntity existing = roundRepository.findById(roundId)
                .orElseThrow(() -> new NotFoundException(ROUND_NOT_FOUND));
        return ROUND_MAPPER.toRoundDtoDefault(existing);
    }

    @Override
    @Transactional
    public void setRoundOpened(Long roundId) {

        RoundEntity existing = roundRepository.findById(roundId)
                .orElseThrow(() -> new NotFoundException(ROUND_NOT_FOUND));

        if (existing.getStatus() != RoundStatusEntity.UPCOMING) {
            throw new ConflictException(ROUND_STATUS_CONFLICT);
        }

        existing.setStatus(RoundStatusEntity.OPEN);
        roundRepository.saveAndFlush(existing);
    }

    @Override
    @Transactional
    public void setRoundClosed(Long roundId) {

        RoundEntity existing = roundRepository.findById(roundId)
                .orElseThrow(() -> new NotFoundException(ROUND_NOT_FOUND));

        if (existing.getStatus() != RoundStatusEntity.OPEN) {
            throw new ConflictException(ROUND_STATUS_CONFLICT);
        }

        existing.setStatus(RoundStatusEntity.CLOSED);
        roundRepository.saveAndFlush(existing);
    }

    @Override
    @Transactional
    public void setRoundFinished(Long roundId) {

        RoundEntity existing = roundRepository.findById(roundId)
                .orElseThrow(() -> new NotFoundException(ROUND_NOT_FOUND));

        if (existing.getStatus() != RoundStatusEntity.CLOSED) {
            throw new ConflictException(ROUND_STATUS_CONFLICT);
        }

        existing.setStatus(RoundStatusEntity.FINISHED);
        roundRepository.saveAndFlush(existing);
    }

    @Override
    @Transactional
    public void setRoundProcessed(Long roundId) {
        RoundEntity existing = roundRepository.findById(roundId)
                .orElseThrow(() -> new NotFoundException(ROUND_NOT_FOUND));

        if (existing.getStatus() != RoundStatusEntity.FINISHED) {
            throw new ConflictException(ROUND_STATUS_CONFLICT);
        }

        existing.setStatus(RoundStatusEntity.PROCESSED);

        Map<Long, UserStatisticsEntity> userStatistics = userStatisticsRepository.findAll()
                .stream()
                .peek(v -> {
                    v.setPrevRank(v.getRank());
                    v.setPrevPts(v.getPts());
                })
                .collect(Collectors.toMap(UserStatisticsEntity::getUserId, statistics -> statistics));

        List<LineupEntity> lineups = lineupRepository.findAllByRoundId(roundId);

        for (LineupEntity lineup : lineups) {
            List<PlayerEntity> players = playerRepository.findAllByLineupId(lineup.getId());

            BigDecimal pts = BigDecimal.ZERO;

            for (PlayerEntity player : players) {
                Optional<PlayerStatisticsEntity> playerStatistics = playerStatisticsRepository.findOneByRoundIdAndPlayerId(roundId, player.getId());
                if (playerStatistics.isPresent()) {
                    if (playerStatistics.get().getRating() != null)
                        pts = pts.add(BigDecimal.valueOf(Double.parseDouble(playerStatistics.get().getRating()) - 6.0d));
                }
            }

            if (userStatistics.containsKey(lineup.getUserId())) {
                userStatistics.get(lineup.getUserId()).setPts(userStatistics.get(lineup.getUserId()).getPts().add(pts));
                userStatistics.get(lineup.getUserId()).setRoundsParticipated(userStatistics.get(lineup.getUserId()).getRoundsParticipated() + 1);
            } else {
                userStatistics.put(lineup.getUserId(), UserStatisticsEntity.builder()
                        .user(UserEntity.builder().id(lineup.getUserId()).build())
                        .prevPts(BigDecimal.ZERO)
                        .pts(pts)
                        .roundsParticipated(1)
                        .build());
            }
        }


        List<UserStatisticsEntity> userStatisticsEntityList = userStatistics.entrySet()
                .stream()
                .sorted((t1, t2) -> {
                    switch (t1.getValue().getPts().compareTo(t2.getValue().getPts())) {
                        case -1:
                            return 1;
                        case 1:
                            return -1;
                        default:
                            return t1.getValue().getRoundsParticipated().compareTo(t2.getValue().getRoundsParticipated());
                    }
                })
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        long rank = 0L;
        int sameRankCount = 0;
        UserStatisticsEntity previous = null;
        for (UserStatisticsEntity userStatisticsItem : userStatisticsEntityList) {

            if (previous != null && userStatisticsItem.getPts().equals(previous.getPts()) && userStatisticsItem.getRoundsParticipated().equals(previous.getRoundsParticipated())) {
                sameRankCount++;
            } else {
                rank += 1 + sameRankCount;
                sameRankCount = 0;
            }

            userStatisticsItem.setRank(rank);
            userStatisticsRepository.saveAndFlush(userStatisticsItem);

            previous = userStatisticsItem;
        }

        roundRepository.saveAndFlush(existing);
    }
}
