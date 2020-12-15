package by.guz.fantasy.football.mapper;

import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.entity.UserEntity;
import by.guz.fantasy.football.entity.enums.UserRoleEntity;
import by.guz.fantasy.football.security.impl.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto.Response.Default toUserDtoDefault(UserEntity source);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    UserEntity toUserEntity(UserDto.Request.SignUp source);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "authorities", ignore = true)
    UserDetailsImpl toUserDetails(Long userId, UserRoleEntity role);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "authorities", ignore = true)
    UserDetailsImpl toUserDetails(UserEntity userEntity);

}
