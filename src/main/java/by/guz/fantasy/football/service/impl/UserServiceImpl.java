package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.entity.UserEntity;
import by.guz.fantasy.football.exception.NotFoundException;
import by.guz.fantasy.football.repository.UserRepository;
import by.guz.fantasy.football.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.exception.Constants.USER_NOT_FOUND;
import static by.guz.fantasy.football.mapper.UserMapper.USER_MAPPER;
import static by.guz.fantasy.football.util.SecurityUtil.getUserId;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto.Response.Default> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(USER_MAPPER::toUserDtoDefault)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto.Response.Default getCurrentUser() {
        UserEntity user = userRepository.findOneById(getUserId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return USER_MAPPER.toUserDtoDefault(user);
    }
}
