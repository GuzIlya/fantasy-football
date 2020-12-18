package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.LineupPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineupPlayerRepository extends JpaRepository<LineupPlayerEntity, Long> {
}
