package by.guz.fantasy.football.mapper;

import by.guz.fantasy.football.dto.RoundDto;
import by.guz.fantasy.football.entity.RoundEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoundMapper {
    RoundMapper ROUND_MAPPER = Mappers.getMapper(RoundMapper.class);

    RoundDto.Response.Default toRoundDtoDefault(RoundEntity source);
}
