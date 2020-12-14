package by.guz.fantasy.football.entity;

import by.guz.fantasy.football.entity.enums.UserRoleEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fantasy_user")
@EqualsAndHashCode(callSuper = false, of = "id")
public class UserEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false, columnDefinition = "citext")
    private String email;

    @Type(type = "enumType")
    @Enumerated(STRING)
    @Column(name = "role", nullable = false, columnDefinition = "user_role")
    private UserRoleEntity role;

    @Column(name = "cash", nullable = false)
    private Long cash;

    @Column (name = "password_hash", nullable = false)
    private String passwordHash;
}
