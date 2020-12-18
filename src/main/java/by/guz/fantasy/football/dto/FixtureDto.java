package by.guz.fantasy.football.dto;

import by.guz.fantasy.football.entity.enums.FixtureStatusEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;
import java.util.List;

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

    public enum External {;

    @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DefaultList {
            List<Default> response;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Default {
            Fixture fixture;
            League league;
            Teams teams;
            Goals goals;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Fixture {
            Long id;
            String date;
            Status status;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class League {
            String round;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Teams {
            Team home;
            Team away;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Team {
            Long id;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Goals {
            Integer home;
            Integer away;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Status {
            @JsonProperty("short")
            String status;
            Integer elapsed;
        }

    }
}
