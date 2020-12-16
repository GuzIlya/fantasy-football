package by.guz.fantasy.football.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player")
@EqualsAndHashCode(callSuper = false, of = "id")
public class PlayerEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private Long externalId;

    @Column(name = "player_name", nullable = false)
    private String name;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private String weight;

    @Column(name = "injured", nullable = false)
    private Boolean injured;

    @Column(name = "photo")
    private String photo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @Column(name = "team_id", insertable = false, updatable = false)
    private Long teamId;
}
