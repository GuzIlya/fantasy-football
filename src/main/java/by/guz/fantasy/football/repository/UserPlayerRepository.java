package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.UserPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlayerRepository extends JpaRepository<UserPlayerEntity, Long> {
}
