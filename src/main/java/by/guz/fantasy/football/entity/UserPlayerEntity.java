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
@Table(name = "user_player")
@EqualsAndHashCode(callSuper = false, of = "id")
public class UserPlayerEntity {

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
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @Column(name = "player_id", insertable = false, updatable = false, nullable = false)
    private Long playerId;

    @Column(name = "purchase_price", nullable = false)
    private Double purchasePrice;

    @Column(name = "purchased_at", insertable = false, updatable = false, nullable = false)
    private Instant purchasedAt;
}
