package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.UserPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPlayerRepository extends JpaRepository<UserPlayerEntity, Long> {

    @Modifying
    @Query(value = "DELETE FROM user_player " +
            "WHERE player_id = :playerId " +
            "AND user_id = :userId ",
            nativeQuery = true)
    void deleteOneByProjectIdAndUserId(@Param("playerId") Long playerId,
                                       @Param("userId") Long userId);

    Optional<UserPlayerEntity> findByUserIdAndPlayerId(Long userId, Long playerId);
}
