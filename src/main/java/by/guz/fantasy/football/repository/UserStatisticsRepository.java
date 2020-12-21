package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.UserStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatisticsEntity, Long> {

    @Query(value = "SELECT * FROM user_statistics " +
            "WHERE user_id = :userId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<UserStatisticsEntity> findOneByUserId(@Param("userId") Long userId);


    @Query(value = "SELECT * FROM user_statistics " +
            "WHERE deleted_at IS NULL ",
            nativeQuery = true)
    List<UserStatisticsEntity> findAll();

    @Query(value = "SELECT * FROM user_statistics " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY rank ",
            nativeQuery = true)
    List<UserStatisticsEntity> findAllOrderedByRank();
}
