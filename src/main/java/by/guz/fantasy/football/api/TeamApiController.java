package by.guz.fantasy.football.api;


import by.guz.fantasy.football.dto.TeamDto;
import by.guz.fantasy.football.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/teams")
public class TeamApiController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamDto.Response.Default>> getAllUsers() {
        List<TeamDto.Response.Default> teams = teamService.getAllTeams();
        return new ResponseEntity<>(teams, OK);
    }
}
