package by.guz.fantasy.football.repository;

import by.guz.fantasy.football.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM fantasy_user " +
            "WHERE deleted_at IS NULL " +
            "ORDER BY created_at ",
            nativeQuery = true)
    List<UserEntity> findAll();

    @Query(value = "SELECT * FROM fantasy_user " +
            "WHERE id = :userId " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<UserEntity> findOneById(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM fantasy_user " +
            "WHERE username = :username " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<UserEntity> findOneByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM fantasy_user " +
            "WHERE email = :email " +
            "AND deleted_at IS NULL ",
            nativeQuery = true)
    Optional<UserEntity> findOneByEmail(@Param("email") String email);
}
