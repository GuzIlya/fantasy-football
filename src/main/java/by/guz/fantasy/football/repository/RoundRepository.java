package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.RoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<RoundEntity, Long> {

    boolean existsByName(String name);
}
