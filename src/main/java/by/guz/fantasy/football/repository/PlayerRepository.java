package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    @Query(value = "SELECT * FROM player " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY created_at ",
            nativeQuery = true)
    List<PlayerEntity> findAll();

    @Query(value = "SELECT * FROM player " +
            "WHERE external_id = :external_id " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<PlayerEntity> findOneByExternalId(@Param("external_id") Long externalId);
}
