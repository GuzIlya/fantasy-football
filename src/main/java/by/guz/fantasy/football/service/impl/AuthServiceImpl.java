package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.TokenDto;
import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.entity.UserEntity;
import by.guz.fantasy.football.exception.BadRequestException;
import by.guz.fantasy.football.exception.NotFoundException;
import by.guz.fantasy.football.repository.UserRepository;
import by.guz.fantasy.football.security.TokenProvider;
import by.guz.fantasy.football.security.TokenValidator;
import by.guz.fantasy.football.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static by.guz.fantasy.football.exception.Constants.USERNAME_NOT_FOUND;
import static by.guz.fantasy.football.exception.Constants.USERNAME_OR_PASSWORD_BAD_REQUEST;

@Service
@AllArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenValidator tokenValidator;

    @Override
    public UserDto.Response.Default signUp(UserDto.Request.SignUp signUpRequest) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public TokenDto.Default login(UserDto.Request.Login loginRequest) {
        UserEntity existing = userRepository.findOneByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new NotFoundException(USERNAME_NOT_FOUND));
        log.info(existing);
        if(!passwordEncoder.matches(loginRequest.getPassword(), existing.getPasswordHash())) {
            log.info(loginRequest.getPassword() + " not matches " + existing.getPasswordHash());
            log.info(passwordEncoder.encode(loginRequest.getPassword()));
            throw new BadRequestException(USERNAME_OR_PASSWORD_BAD_REQUEST);
        }
        return tokenProvider.createToken(existing);
    }

    @Override
    public TokenDto.Default refreshToken(TokenDto.Default token) {
        return null;
    }
}
