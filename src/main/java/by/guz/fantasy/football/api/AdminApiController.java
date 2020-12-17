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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminApiController {

    private final AdminService adminService;

    @PostMapping("/players/update")
    public ResponseEntity<Object> updatePlayers() throws JsonProcessingException {
        adminService.updatePlayers();
        return new ResponseEntity<>(CREATED);
    }

    @PostMapping("/teams/update")
    public ResponseEntity<Object> updateTeams() throws JsonProcessingException {
        adminService.updateTeams();
        return new ResponseEntity<>(CREATED);
    }
}
