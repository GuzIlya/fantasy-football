package by.guz.fantasy.football.api;

import by.guz.fantasy.football.dto.TokenDto;
import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthApiController {
    public static final String LOGIN_PATH = "/api/v1/auth/login";
    public static final String REFRESH_TOKEN_PATH = "/api/v1/auth/refresh-token";
    public static final String SIGNUP_PATH = "/api/v1/auth/signup";

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto.Response.Default> signUp(@Valid @RequestBody final UserDto.Request.SignUp body) {
        UserDto.Response.Default user = authService.signUp(body);
        return new ResponseEntity<>(user, CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto.Default> login(@Valid @RequestBody final UserDto.Request.Login body) {
        TokenDto.Default token = authService.login(body);
        return new ResponseEntity<>(token, CREATED);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto.Default> refreshToken(@Valid @RequestBody final TokenDto.Default body) {
        TokenDto.Default token = authService.refreshToken(body);
        return new ResponseEntity<>(token, CREATED);
    }

}
