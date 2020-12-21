package by.guz.fantasy.football.api;

import by.guz.fantasy.football.dto.RoundDto;
import by.guz.fantasy.football.service.RoundService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/rounds")
public class RoundApiController {

    private final RoundService roundService;

    @GetMapping
    public ResponseEntity<List<RoundDto.Response.Default>> getAllRounds() {
        List<RoundDto.Response.Default> rounds = roundService.getAllRounds();
        return new ResponseEntity<>(rounds, OK);
    }

    @GetMapping("/{roundId}")
    public ResponseEntity<RoundDto.Response.Default> getRoundById(@PathVariable Long roundId) {
        RoundDto.Response.Default round = roundService.getRoundById(roundId);
        return new ResponseEntity<>(round, OK);
    }

    @PostMapping("/{roundId}/process")
    public ResponseEntity<Object> setRoundProcessed(@PathVariable Long roundId) {
        roundService.setRoundProcessed(roundId);
        return new ResponseEntity<>(CREATED);
    }
}
