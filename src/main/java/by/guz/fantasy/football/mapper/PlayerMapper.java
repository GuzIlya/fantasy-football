package by.guz.fantasy.football.mapper;

import by.guz.fantasy.football.dto.PlayerDto;
import by.guz.fantasy.football.entity.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerMapper {
    PlayerMapper PLAYER_MAPPER = Mappers.getMapper(PlayerMapper.class);

    PlayerDto.Response.Default toPlayerDtoDefault(PlayerEntity source);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "birthdate", source = "birth.date")
    PlayerEntity toPlayerEntity(PlayerDto.External.Default source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "birthdate", source = "birth.date")
    PlayerEntity updateEntity(PlayerDto.External.Default source, @MappingTarget PlayerEntity target);
}
