package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.repository.UserRepository;
import by.guz.fantasy.football.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.mapper.UserMapper.USER_MAPPER;

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
}
