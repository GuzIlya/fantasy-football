package by.guz.fantasy.football.api;

import by.guz.fantasy.football.dto.PlayerDto;
import by.guz.fantasy.football.dto.TeamDto;
import by.guz.fantasy.football.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminApiController {

    private final AdminService adminService;

    @PostMapping("/players/update")
    public ResponseEntity<List<PlayerDto.Response.Default>> updatePlayers() throws JsonProcessingException {
        List<PlayerDto.Response.Default> players = adminService.updatePlayers();
        return new ResponseEntity<>(players, OK);
    }

    @PostMapping("/teams/update")
    public ResponseEntity<List<TeamDto.Response.Default>> updateTeams() throws JsonProcessingException {
        List<TeamDto.Response.Default> teams = adminService.updateTeams();
        return new ResponseEntity<>(teams, OK);
    }
}
