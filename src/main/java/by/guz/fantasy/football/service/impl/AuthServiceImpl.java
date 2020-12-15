package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.configuration.AppConfiguration;
import by.guz.fantasy.football.dto.TokenDto;
import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.entity.UserEntity;
import by.guz.fantasy.football.entity.enums.UserRoleEntity;
import by.guz.fantasy.football.exception.BadRequestException;
import by.guz.fantasy.football.exception.ConflictException;
import by.guz.fantasy.football.exception.NotFoundException;
import by.guz.fantasy.football.exception.UnauthorizedException;
import by.guz.fantasy.football.repository.UserRepository;
import by.guz.fantasy.football.security.TokenProvider;
import by.guz.fantasy.football.security.TokenValidator;
import by.guz.fantasy.football.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static by.guz.fantasy.football.exception.Constants.*;
import static by.guz.fantasy.football.mapper.UserMapper.USER_MAPPER;

@Service
@AllArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final AppConfiguration appConfiguration;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenValidator tokenValidator;

    @Override
    @Transactional
    public UserDto.Response.Default signUp(UserDto.Request.SignUp signUpRequest) {
        String email = signUpRequest.getEmail().toLowerCase();

        if (userRepository.findOneByEmail(email).isPresent()) {
            throw new ConflictException(USER_EMAIL_CONFLICT);
        }

        if (userRepository.findOneByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new ConflictException(USER_USERNAME_CONFLICT);
        }

        UserEntity user = USER_MAPPER.toUserEntity(signUpRequest);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCash(appConfiguration.getGame().getStartCash());
        user.setRole(UserRoleEntity.USER);
        return USER_MAPPER.toUserDtoDefault(userRepository.saveAndFlush(user));
    }

    @Override
    @Transactional(readOnly = true)
    public TokenDto.Default login(UserDto.Request.Login loginRequest) {
        UserEntity existing = userRepository.findOneByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new NotFoundException(USER_USERNAME_NOT_FOUND));
        log.info(existing);
        if (!passwordEncoder.matches(loginRequest.getPassword(), existing.getPasswordHash())) {
            log.info(loginRequest.getPassword() + " not matches " + existing.getPasswordHash());
            log.info(passwordEncoder.encode(loginRequest.getPassword()));
            throw new BadRequestException(USERNAME_OR_PASSWORD_BAD_REQUEST);
        }
        return tokenProvider.createToken(existing);
    }

    @Override
    public TokenDto.Default refreshToken(TokenDto.Default token) {
        if (!tokenValidator.validateToken(token.getRefreshToken())) {
            throw new UnauthorizedException(REFRESH_TOKEN_UNAUTHORIZED);
        }
        return tokenProvider.refreshToken(token.getRefreshToken());
    }
}
