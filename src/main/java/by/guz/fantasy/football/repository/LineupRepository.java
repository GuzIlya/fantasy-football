package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.LineupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineupRepository extends JpaRepository<LineupEntity, Long> {

    @Query(value = "SELECT * FROM lineup " +
            "WHERE user_id = :userId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    List<LineupEntity> findAllByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM lineup " +
            "WHERE user_id = :userId " +
            "AND round_id = :roundId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<LineupEntity> findByUserIdAndRoundId(@Param("userId") Long userId, @Param("roundId") Long roundId);
}
