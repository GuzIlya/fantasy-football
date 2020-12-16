package by.guz.fantasy.football.mapper;

import by.guz.fantasy.football.dto.TeamDto;
import by.guz.fantasy.football.entity.TeamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeamMapper {
    TeamMapper TEAM_MAPPER = Mappers.getMapper(TeamMapper.class);

    TeamDto.Response.Default toTeamDtoDefault(TeamEntity source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    TeamEntity toTeamEntity(TeamDto.External.Default source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    TeamEntity updateEntity(TeamDto.External.Default source, @MappingTarget TeamEntity target);
}
