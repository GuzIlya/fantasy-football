package by.guz.fantasy.football.service;

import by.guz.fantasy.football.dto.LineupDto;

import java.util.List;

public interface LineupService {

    List<LineupDto.Response.Default> getCurrentUserLineups();

    LineupDto.Response.Default getCurrentUserLineupById(Long lineupId);

    void setLineupToCurrentUser(LineupDto.Request.Default dto);

}
