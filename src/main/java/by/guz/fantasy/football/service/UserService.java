package by.guz.fantasy.football.service;

import by.guz.fantasy.football.dto.PlayerDto;
import by.guz.fantasy.football.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto.Response.Default> getAllUsers();

    UserDto.Response.Default getUserById(Long userId);

    UserDto.Response.Default getCurrentUser();

    PlayerDto.Response.Default purchasePlayerToCurrentUser(Long playerId);

    void sellPlayerToCurrentUser(Long playerId);

}
