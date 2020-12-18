package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.RoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<RoundEntity, Long> {


    @Query(value = "SELECT * FROM round " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY created_at ",
            nativeQuery = true)
    List<RoundEntity> findAll();

    boolean existsByName(String name);
}
