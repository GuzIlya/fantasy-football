package by.guz.fantasy.football.entity;


import lombok.*;

import javax.persistence.*;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lineup_player")
@EqualsAndHashCode(callSuper = false, of = "id")
public class LineupPlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @Column(name = "number", nullable = false)
    private Integer number;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lineup_id", nullable = false)
    private LineupEntity lineup;

    @Column(name = "lineup_id", insertable = false, updatable = false, nullable = false)
    private Long lineupId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @Column(name = "player_id", insertable = false, updatable = false, nullable = false)
    private Long playerId;

    @Column(name = "added_at", insertable = false, updatable = false, nullable = false)
    private Instant addedAt;
}
