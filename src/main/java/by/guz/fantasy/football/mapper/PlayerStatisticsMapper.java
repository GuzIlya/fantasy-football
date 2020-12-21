package by.guz.fantasy.football.mapper;

import by.guz.fantasy.football.dto.PlayerStatisticsDto;
import by.guz.fantasy.football.entity.PlayerStatisticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerStatisticsMapper {
    PlayerStatisticsMapper PLAYER_STATISTICS_MAPPER = Mappers.getMapper(PlayerStatisticsMapper.class);

    PlayerStatisticsDto.Response.Default toPlayerStatisticsDefault(PlayerStatisticsEntity source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "minutesPlayed", source = "games.minutes")
    @Mapping(target = "rating", source = "games.rating")
    @Mapping(target = "offsides", source = "offsides")
    @Mapping(target = "shotsTotal", source = "shots.total")
    @Mapping(target = "shotsOn", source = "shots.on")
    @Mapping(target = "goalsTotal", source = "goals.total")
    @Mapping(target = "goalsConceded", source = "goals.conceded")
    @Mapping(target = "assists", source = "goals.assists")
    @Mapping(target = "saves", source = "goals.saves")
    @Mapping(target = "passesTotal", source = "passes.total")
    @Mapping(target = "passesKey", source = "passes.key")
    @Mapping(target = "passesAccuracy", source = "passes.accuracy")
    @Mapping(target = "tackles", source = "tackles.total")
    @Mapping(target = "blocks", source = "tackles.blocks")
    @Mapping(target = "interceptions", source = "tackles.interceptions")
    @Mapping(target = "duelsTotal", source = "duels.total")
    @Mapping(target = "duelsWon", source = "duels.won")
    @Mapping(target = "dribblesAttempts", source = "dribbles.attempts")
    @Mapping(target = "dribblesSuccess", source = "dribbles.success")
    @Mapping(target = "dribblesPast", source = "dribbles.past")
    @Mapping(target = "foulsDrawn", source = "fouls.drawn")
    @Mapping(target = "foulsCommitted", source = "fouls.committed")
    @Mapping(target = "cardsYellow", source = "cards.yellow")
    @Mapping(target = "cardsRed", source = "cards.red")
    @Mapping(target = "penaltyScored", source = "penalty.scored")
    @Mapping(target = "penaltyMissed", source = "penalty.missed")
    @Mapping(target = "penaltySaved", source = "penalty.saved")
    PlayerStatisticsEntity toPlayerStatisticsEntity(PlayerStatisticsDto.External.StatisticsExternal source);
}
