package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.PlayerStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerStatisticsRepository extends JpaRepository<PlayerStatisticsEntity, Long> {

    void deleteAllByFixtureId(Long fixtureId);

    List<PlayerStatisticsEntity> findAllByFixtureId(Long fixtureId);

    @Query(value = "SELECT * FROM player_statistics ps " +
            "LEFT JOIN fixture fi ON fi.id = ps.fixture_id " +
            "WHERE fi.round_id = :roundId " +
            "AND ps.player_id = :playerId " +
            "AND fi.deleted_at IS NULL " +
            "AND ps.deleted_at IS NULL ",
            nativeQuery = true)
    Optional<PlayerStatisticsEntity> findOneByRoundIdAndPlayerId(@Param("roundId") Long roundId, @Param("playerId") Long playerId);
}
