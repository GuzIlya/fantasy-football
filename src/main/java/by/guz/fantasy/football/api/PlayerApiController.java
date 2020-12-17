package by.guz.fantasy.football.api;

import by.guz.fantasy.football.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/players")
@Validated
public class PlayerApiController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPlayers(
            @Min(1) @Valid @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @Min(10) @Max(30) @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @Size(min = 3) @Valid @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "minAge", required = false) Integer minAge,
            @RequestParam(value = "maxAge", required = false) Integer maxAge,
            @RequestParam(value = "nationality", required = false) String nationality,
            @RequestParam(value = "minCost", required = false) Double minCost,
            @RequestParam(value = "maxCost", required = false) Double maxCost,
            @RequestParam(value = "team", required = false) Long teamId) {
        Map<String, Object> players = playerService.getAllPlayersFounded(page, size, search, minAge, maxAge, nationality,
                minCost, maxCost, teamId);
        return new ResponseEntity<>(players, OK);
    }
}
