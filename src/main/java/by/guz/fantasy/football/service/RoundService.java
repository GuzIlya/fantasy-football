package by.guz.fantasy.football.service;

import by.guz.fantasy.football.dto.RoundDto;

import java.util.List;

public interface RoundService {

    List<RoundDto.Response.Default> getAllRounds();

    RoundDto.Response.Default getRoundById(Long roundId);

    void setRoundOpened(Long roundId);

    void setRoundClosed(Long roundId);

    void setRoundFinished(Long roundId);

    void setRoundProcessed(Long roundId);
}
