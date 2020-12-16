package by.guz.fantasy.football.api;

import by.guz.fantasy.football.dto.TeamDto;
import by.guz.fantasy.football.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/players/update")
    public ResponseEntity<JsonNode> updatePlayers() {
        JsonNode node = adminService.updatePlayers();
        return new ResponseEntity<>(node, OK);
    }

    @GetMapping("/teams/update")
    public ResponseEntity<List<TeamDto.Response.Default>> updateTeams() throws JsonProcessingException {
        List<TeamDto.Response.Default> node = adminService.updateTeams();
        return new ResponseEntity<>(node, OK);
    }
}
