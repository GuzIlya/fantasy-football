package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.PlayerStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatisticsRepository extends JpaRepository<PlayerStatisticsEntity, Long> {

    void deleteAllByFixtureId(Long fixtureId);

    List<PlayerStatisticsEntity> findAllByFixtureId(Long fixtureId);
}
