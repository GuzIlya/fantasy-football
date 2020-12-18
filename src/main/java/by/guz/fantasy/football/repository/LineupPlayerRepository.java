package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.LineupPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineupPlayerRepository extends JpaRepository<LineupPlayerEntity, Long> {

    Optional<LineupPlayerEntity> findByLineupIdAndNumber(Long lineupId, Integer number);

    void deleteAllByLineupId(Long lineupId);

    List<LineupPlayerEntity> findAllByLineupId(long lineupId);
}
