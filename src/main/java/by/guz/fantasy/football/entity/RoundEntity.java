package by.guz.fantasy.football.entity;

import by.guz.fantasy.football.entity.enums.RoundStatusEntity;
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
@Table(name = "round")
@EqualsAndHashCode(callSuper = false, of = "id")
public class RoundEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Type(type = "enumType")
    @Enumerated(STRING)
    @Column(name = "status", nullable = false, columnDefinition = "round_status")
    private RoundStatusEntity status;
}
