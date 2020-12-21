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
@Table(name = "player_statistics")
@EqualsAndHashCode(callSuper = false, of = "id")
public class PlayerStatisticsEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @Column(name = "player_id", insertable = false, updatable = false, nullable = false)
    private Long playerId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "fixture_id", nullable = false)
    private FixtureEntity fixture;

    @Column(name = "fixture_id", insertable = false, updatable = false, nullable = false)
    private Long fixtureId;

    @Column(name = "minutes_played")
    Integer minutesPlayed;

    @Column(name = "rating")
    String rating;

    @Column(name = "offsides")
    Integer offsides;

    @Column(name = "shots_total")
    Integer shotsTotal;

    @Column(name = "shots_on")
    Integer shotsOn;

    @Column(name = "goals_total")
    Integer goalsTotal;

    @Column(name = "goals_conceded")
    Integer goalsConceded;

    @Column(name = "assists")
    Integer assists;

    @Column(name = "saves")
    Integer saves;

    @Column(name = "passes_total")
    Integer passesTotal;

    @Column(name = "passes_key")
    Integer passesKey;

    @Column(name = "passes_accuracy")
    String passesAccuracy;

    @Column(name = "tackles")
    Integer tackles;

    @Column(name = "blocks")
    Integer blocks;

    @Column(name = "interceptions")
    Integer interceptions;

    @Column(name = "duels_total")
    Integer duelsTotal;

    @Column(name = "duels_won")
    Integer duelsWon;

    @Column(name = "dribbles_attempts")
    Integer dribblesAttempts;

    @Column(name = "dribbles_success")
    Integer dribblesSuccess;

    @Column(name = "dribbles_past")
    Integer dribblesPast;

    @Column(name = "fouls_drawn")
    Integer foulsDrawn;

    @Column(name = "fouls_committed")
    Integer foulsCommitted;

    @Column(name = "cards_yellow")
    Integer cardsYellow;

    @Column(name = "cards_red")
    Integer cardsRed;

    @Column(name = "penalty_scored")
    Integer penaltyScored;

    @Column(name = "penalty_missed")
    Integer penaltyMissed;

    @Column(name = "penalty_saved")
    Integer penaltySaved;
}
