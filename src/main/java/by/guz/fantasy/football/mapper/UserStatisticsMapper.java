package by.guz.fantasy.football.mapper;

import by.guz.fantasy.football.dto.UserStatisticsDto;
import by.guz.fantasy.football.entity.UserStatisticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserStatisticsMapper {
    UserStatisticsMapper USER_STATISTICS_MAPPER = Mappers.getMapper(UserStatisticsMapper.class);

    UserStatisticsDto.Response.Default toUserStatisticsDtoDefault(UserStatisticsEntity source);
}
