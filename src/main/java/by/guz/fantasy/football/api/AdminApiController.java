package by.guz.fantasy.football.api;

import by.guz.fantasy.football.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

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

    @PostMapping("/rounds/update")
    public ResponseEntity<Object> updateRounds() throws JsonProcessingException {
        adminService.updateRounds();
        return new ResponseEntity<>(CREATED);
    }

    @PostMapping("/fixtures/update")
    public ResponseEntity<Object> updateFixtures() throws JsonProcessingException {
        adminService.updateFixtures();
        return new ResponseEntity<>(CREATED);
    }


    @PostMapping("/fixtures/{fixtureId}/update")
    public ResponseEntity<Object> updatePlayerStatistics(@PathVariable Long fixtureId) throws JsonProcessingException {
        adminService.updatePlayerStatistics(fixtureId);
        return new ResponseEntity<>(CREATED);
    }
}
