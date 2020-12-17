package by.guz.fantasy.football.api;

import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserApiController {

    private final UserService userService;

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
}
