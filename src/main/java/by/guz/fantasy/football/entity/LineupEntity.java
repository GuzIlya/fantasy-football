package by.guz.fantasy.football.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lineup")
@EqualsAndHashCode(callSuper = false, of = "id")
public class LineupEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    private Long userId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "round_id", nullable = false)
    private RoundEntity round;

    @Column(name = "round_id", insertable = false, updatable = false, nullable = false)
    private Long roundId;
}
