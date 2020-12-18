package by.guz.fantasy.football.entity;

import by.guz.fantasy.football.entity.enums.FixtureStatusEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fixture")
@EqualsAndHashCode(callSuper = false, of = "id")
public class FixtureEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private Long externalId;

    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "round_id", nullable = false)
    private RoundEntity round;

    @Column(name = "round_id", insertable = false, updatable = false, nullable = false)
    private Long roundId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "home_team", nullable = false)
    private TeamEntity homeTeam;

    @Column(name = "home_team", insertable = false, updatable = false, nullable = false)
    private Long homeTeamId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "away_team", nullable = false)
    private TeamEntity awayTeam;

    @Column(name = "away_team", insertable = false, updatable = false, nullable = false)
    private Long awayTeamId;

    @Column(name = "home_goals")
    private Integer homeGoals;

    @Column(name = "away_goals")
    private Integer awayGoals;

    @Type(type = "enumType")
    @Enumerated(STRING)
    @Column(name = "status", nullable = false, columnDefinition = "fixture_status")
    private FixtureStatusEntity status;

    @Column(name = "elapsed")
    private Integer elapsed;
}
