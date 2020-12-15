package by.guz.fantasy.football.service;

import by.guz.fantasy.football.dto.TokenDto;
import by.guz.fantasy.football.dto.UserDto;

public interface AuthService {

    UserDto.Response.Default signUp(UserDto.Request.SignUp signUpRequest);

    TokenDto.Default login(UserDto.Request.Login loginRequest);

    TokenDto.Default refreshToken(TokenDto.Default token);
}
