package by.guz.fantasy.football.mapper;

import by.guz.fantasy.football.dto.FixtureDto;
import by.guz.fantasy.football.entity.FixtureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FixtureMapper {
    FixtureMapper FIXTURE_MAPPER = Mappers.getMapper(FixtureMapper.class);

    FixtureDto.Response.Default toFixtureDtoDefault(FixtureEntity source);
}
