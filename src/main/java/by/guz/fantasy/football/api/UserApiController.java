package by.guz.fantasy.football.api;

import by.guz.fantasy.football.dto.PlayerDto;
import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.service.PlayerService;
import by.guz.fantasy.football.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserApiController {

    private final UserService userService;
    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<UserDto.Response.Default>> getAllUsers() {
        List<UserDto.Response.Default> users = userService.getAllUsers();
        return new ResponseEntity<>(users, OK);
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
}
