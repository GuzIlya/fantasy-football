package by.guz.fantasy.football.mapper;

import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto.Response.Default toUserDtoDefault(UserEntity source);
}
