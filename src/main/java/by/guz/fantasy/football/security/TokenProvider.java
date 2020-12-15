package by.guz.fantasy.football.security;

import by.guz.fantasy.football.dto.TokenDto;
import by.guz.fantasy.football.entity.UserEntity;
import by.guz.fantasy.football.entity.enums.UserRoleEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenProvider {

    TokenDto.Default createToken(UserEntity userEntity);

    TokenDto.Default createToken(UserDetails userDetails);

    TokenDto.Default refreshToken(String refreshToken);

    UserDetails buildUserDetailsByToken(String token);

    Long getUserIdByToken(String token);

    UserRoleEntity getUserRoleByToken(String token);

}
