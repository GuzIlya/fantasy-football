package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.FixtureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FixtureRepository extends JpaRepository<FixtureEntity, Long> {

    @Query(value = "SELECT * FROM fixture " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY created_at ",
            nativeQuery = true)
    List<FixtureEntity> findAll();
}
