package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>, JpaSpecificationExecutor<PlayerEntity> {

    @Query(value = "SELECT * FROM player " +
            "LEFT JOIN user_player up ON player.id = up.player_id " +
            "WHERE up.user_id = :userId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    List<PlayerEntity> findAllByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT(EXISTS(SELECT * FROM player " +
            "LEFT JOIN user_player up ON player.id = up.player_id " +
            "WHERE up.user_id = :userId " +
            "AND player.id = :id " +
            "AND deleted_at IS NULL)) ",
            nativeQuery = true)
    boolean existsByUserIdAndId(@Param("userId") Long userId, @Param("id") Long id);

    @Query(value = "SELECT * FROM player " +
            "WHERE id = :playerId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<PlayerEntity> findOneById(@Param("playerId") Long playerId);
}
