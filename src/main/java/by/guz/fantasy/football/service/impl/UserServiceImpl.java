package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.PlayerDto;
import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.entity.PlayerEntity;
import by.guz.fantasy.football.entity.UserEntity;
import by.guz.fantasy.football.entity.UserPlayerEntity;
import by.guz.fantasy.football.exception.ConflictException;
import by.guz.fantasy.football.exception.NotFoundException;
import by.guz.fantasy.football.repository.PlayerRepository;
import by.guz.fantasy.football.repository.UserPlayerRepository;
import by.guz.fantasy.football.repository.UserRepository;
import by.guz.fantasy.football.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.exception.Constants.*;
import static by.guz.fantasy.football.mapper.PlayerMapper.PLAYER_MAPPER;
import static by.guz.fantasy.football.mapper.UserMapper.USER_MAPPER;
import static by.guz.fantasy.football.util.SecurityUtil.getUserId;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final UserPlayerRepository userPlayerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto.Response.Default> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(USER_MAPPER::toUserDtoDefault)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto.Response.Default getCurrentUser() {
        UserEntity user = userRepository.findOneById(getUserId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return USER_MAPPER.toUserDtoDefault(user);
    }

    @Override
    @Transactional
    public PlayerDto.Response.Default purchasePlayerToCurrentUser(Long playerId) {

        UserEntity user = userRepository.findOneById(getUserId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        PlayerEntity player = playerRepository.findOneById(playerId)
                .orElseThrow(() -> new NotFoundException(PLAYER_NOT_FOUND));

        if (user.getCash() < player.getCost())
            throw new ConflictException(NOT_ENOUGH_CASH_CONFLICT);

        user.setCash(user.getCash() - player.getCost());
        userRepository.saveAndFlush(user);

        UserPlayerEntity userPlayer = UserPlayerEntity.builder()
                .user(UserEntity.builder().id(getUserId()).build())
                .player(PlayerEntity.builder().id(playerId).build())
                .purchasePrice(player.getCost())
                .build();
        userPlayerRepository.saveAndFlush(userPlayer);

        return PLAYER_MAPPER.toPlayerDtoDefault(player);
    }
}
