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
                .collect(Collectors.toMap(UserStatisticsEntity::getUserId, statistics -> statistics));

        userStatistics.forEach((k, v) -> {
            v.setPrevRank(v.getRank());
            v.setPrevPts(v.getPts());
        });


        List<LineupEntity> lineups = lineupRepository.findAllByRoundId(roundId);

        for (LineupEntity lineup : lineups) {
            List<PlayerEntity> players = playerRepository.findAllByLineupId(lineup.getId());

            Double pts = 0.0;

            for (PlayerEntity player : players) {
                Optional<PlayerStatisticsEntity> playerStatistics = playerStatisticsRepository.findOneByRoundIdAndPlayerId(roundId, player.getId());
                if (playerStatistics.isPresent()) {
                    pts += Double.parseDouble(playerStatistics.get().getRating()) - 6.0d;
                }
            }

            if (userStatistics.containsKey(lineup.getUserId())) {
                userStatistics.get(lineup.getUserId()).setPts(userStatistics.get(lineup.getUserId()).getPts() + pts);
                userStatistics.get(lineup.getUserId()).setRoundsParticipated(userStatistics.get(lineup.getUserId()).getRoundsParticipated() + 1);
            } else {
                userStatistics.put(lineup.getUserId(), UserStatisticsEntity.builder()
                        .user(UserEntity.builder().id(lineup.getUserId()).build())
                        .prevPts(0.0)
                        .pts(pts)
                        .roundsParticipated(1)
                        .build());
            }
        }


        List<UserStatisticsEntity> userStatisticsEntityList = userStatistics.entrySet()
                .stream()
                .sorted((t1, t2) -> {
                    if (t1.getValue().getPts() > t2.getValue().getPts())
                        return -1;
                    else if (t1.getValue().getPts() < t2.getValue().getPts())
                        return 1;
                    return 0;
                })
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        Long rank = 1L;
        for (UserStatisticsEntity userStatisticsItem : userStatisticsEntityList) {
            userStatisticsItem.setRank(rank);
            rank++;
            userStatisticsRepository.saveAndFlush(userStatisticsItem);
        }

        roundRepository.saveAndFlush(existing);
    }
}
