package by.guz.fantasy.football.api;

import by.guz.fantasy.football.dto.RoundDto;
import by.guz.fantasy.football.service.RoundService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
