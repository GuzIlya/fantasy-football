package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM fantasy_user " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY created_at ",
            nativeQuery = true)
    List<UserEntity> findAll();
}
