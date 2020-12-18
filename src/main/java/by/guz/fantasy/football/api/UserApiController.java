package by.guz.fantasy.football.api;

import by.guz.fantasy.football.dto.LineupDto;
import by.guz.fantasy.football.dto.PlayerDto;
import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.service.LineupService;
import by.guz.fantasy.football.service.PlayerService;
import by.guz.fantasy.football.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "/api/v1/users")
public class UserApiController {

    private final UserService userService;
    private final PlayerService playerService;
    private final LineupService lineupService;

    @GetMapping
    public ResponseEntity<List<UserDto.Response.Default>> getAllUsers() {
        List<UserDto.Response.Default> users = userService.getAllUsers();
        return new ResponseEntity<>(users, OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto.Response.Default> getUserById(@PathVariable Long userId) {
        UserDto.Response.Default user = userService.getUserById(userId);
        return new ResponseEntity<>(user, OK);
    }


    @GetMapping("/current")
    public ResponseEntity<UserDto.Response.Default> getCurrentUser() {
        UserDto.Response.Default user = userService.getCurrentUser();
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/current/players")
    public ResponseEntity<List<PlayerDto.Response.Default>> getCurrentUserPlayers() {
        List<PlayerDto.Response.Default> players = playerService.getCurrentUserPlayers();
        return new ResponseEntity<>(players, OK);
    }

    @PostMapping("/current/players/{playerId}")
    public ResponseEntity<PlayerDto.Response.Default> purchasePlayerToCurrentUser(@PathVariable Long playerId) {
        PlayerDto.Response.Default player = userService.purchasePlayerToCurrentUser(playerId);
        return new ResponseEntity<>(player, CREATED);
    }

    @DeleteMapping("/current/players/{playerId}")
    public ResponseEntity<Object> sellPlayerToCurrentUser(@PathVariable Long playerId) {
        userService.sellPlayerToCurrentUser(playerId);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping("/current/lineups")
    public ResponseEntity<List<LineupDto.Response.Default>> getCurrentUserLineups() {
        List<LineupDto.Response.Default> lineups = lineupService.getCurrentUserLineups();
        return new ResponseEntity<>(lineups, OK);
    }

    @PostMapping("/current/lineups")
    public ResponseEntity<Object> setLineupToCurrentUser(@RequestBody @Valid LineupDto.Request.Default body) {
        lineupService.setLineupToCurrentUser(body);
        return new ResponseEntity<>(CREATED);
    }

}
