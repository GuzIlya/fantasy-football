package by.guz.fantasy.football.dto;

import by.guz.fantasy.football.entity.enums.FixtureStatusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

public enum FixtureDto {;
    private interface Id { @Positive Long getId(); }
    private interface Date { @NotBlank Instant getDate(); }
    private interface RoundId { @NotNull Long getRoundId(); }
    private interface HomeTeam { @NotNull TeamDto.Response.Default getHomeTeam(); }
    private interface AwayTeam { @NotNull TeamDto.Response.Default getAwayTeam(); }
    private interface HomeGoals { Integer getHomeGoals(); }
    private interface AwayGoals { Integer getAwayGoals(); }
    private interface Status { FixtureStatusEntity getStatus(); }
    private interface Elapsed { Integer getElapsed(); }

    public enum Response {;

        @Getter @Setter @NoArgsConstructor
        public static class Default implements Id, Date, RoundId, HomeTeam, AwayTeam, HomeGoals, AwayGoals, Status, Elapsed{
            Long id;
            Instant date;
            Long roundId;
            TeamDto.Response.Default homeTeam;
            TeamDto.Response.Default awayTeam;
            Integer homeGoals;
            Integer awayGoals;
            FixtureStatusEntity status;
            Integer elapsed;
        }
    }
}
