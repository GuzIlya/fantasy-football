package by.guz.fantasy.football.mapper;

import by.guz.fantasy.football.dto.FixtureDto;
import by.guz.fantasy.football.entity.FixtureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FixtureMapper {
    FixtureMapper FIXTURE_MAPPER = Mappers.getMapper(FixtureMapper.class);

    FixtureDto.Response.Default toFixtureDtoDefault(FixtureEntity source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "fixture.id")
    @Mapping(target = "homeGoals", source = "goals.home")
    @Mapping(target = "awayGoals", source = "goals.away")
    @Mapping(target = "elapsed", source = "fixture.status.elapsed")
    FixtureEntity updateEntity(FixtureDto.External.Default source, @MappingTarget FixtureEntity target);



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "fixture.id")
    @Mapping(target = "homeGoals", source = "goals.home")
    @Mapping(target = "awayGoals", source = "goals.away")
    @Mapping(target = "elapsed", source = "fixture.status.elapsed")
    FixtureEntity toFixtureEntity(FixtureDto.External.Default source);
}
