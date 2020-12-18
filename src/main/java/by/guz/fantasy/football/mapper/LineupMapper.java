package by.guz.fantasy.football.mapper;

import by.guz.fantasy.football.dto.LineupDto;
import by.guz.fantasy.football.entity.LineupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LineupMapper {
    LineupMapper LINEUP_MAPPER = Mappers.getMapper(LineupMapper.class);

    LineupDto.Response.Default toLineupDtoDefault(LineupEntity source);
}
