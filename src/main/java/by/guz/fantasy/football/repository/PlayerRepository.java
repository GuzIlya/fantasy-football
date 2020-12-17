package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>, JpaSpecificationExecutor<PlayerEntity> {

    @Query(value = "SELECT * FROM player " +
            "LEFT JOIN user_player up ON player.id = up.player_id " +
            "WHERE up.user_id = :userId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    List<PlayerEntity> findAllByUserId(@Param("userId") Long userId);
}
