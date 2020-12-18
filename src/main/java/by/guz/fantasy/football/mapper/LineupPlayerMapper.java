package by.guz.fantasy.football.mapper;

import by.guz.fantasy.football.dto.LineupPlayerDto;
import by.guz.fantasy.football.entity.LineupPlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LineupPlayerMapper {
    LineupPlayerMapper LINEUP_PLAYER_MAPPER = Mappers.getMapper(LineupPlayerMapper.class);

    LineupPlayerDto.Extended toLineupPlayerDtoExtended(LineupPlayerEntity source);
}
