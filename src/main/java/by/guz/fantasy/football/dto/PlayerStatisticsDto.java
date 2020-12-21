package by.guz.fantasy.football.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public enum PlayerStatisticsDto {;
    private interface Id { @Positive Long getId(); }
    private interface Player { @NotNull PlayerDto.Response.Default getPlayer(); }
    private interface Fixture { @NotNull FixtureDto.Response.Default getFixture(); }

    public enum Response {;

        @Getter @Setter @NoArgsConstructor
        public static class Default implements Id, Player, Fixture {
            Long id;
            PlayerDto.Response.Default player;
            FixtureDto.Response.Default fixture;

            Integer minutesPlayed;
            String rating;
            Integer offsides;
            Integer shotsTotal;
            Integer shotsOn;
            Integer goalsTotal;
            Integer goalsConceded;
            Integer assists;
            Integer saves;
            Integer passesTotal;
            Integer passesKey;
            String passesAccuracy;
            Integer tackles;
            Integer blocks;
            Integer interceptions;
            Integer duelsTotal;
            Integer duelsWon;
            Integer dribblesAttempts;
            Integer dribblesSuccess;
            Integer dribblesPast;
            Integer foulsDrawn;
            Integer foulsCommitted;
            Integer cardsYellow;
            Integer cardsRed;
            Integer penaltyScored;
            Integer penaltyMissed;
            Integer penaltySaved;
        }
    }

    public enum External {;

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DefaultList {
            List<DefaultTeamList> response;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DefaultTeamList {
            List<Default> players;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Default {
            List<Players> players;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Players {
            PlayerExternal player;
            List<StatisticsExternal> statistics;
        }


        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PlayerExternal {
            Long id;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class StatisticsExternal {
            Games games;
            Integer offsides;
            Shots shots;
            Goals goals;
            Passes passes;
            Tackles tackles;
            Duels duels;
            Dribbles dribbles;
            Fouls fouls;
            Cards cards;
            Penalty penalty;

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Games {
                Integer minutes;
                String rating;
            }

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Shots {
                Integer total;
                Integer on;
            }

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Goals {
                Integer total;
                Integer conceded;
                Integer assists;
                Integer saves;
            }

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Passes {
                Integer total;
                Integer key;
                String accuracy;
            }

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Tackles {
                Integer total;
                Integer blocks;
                Integer interceptions;
            }

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Duels {
                Integer total;
                Integer won;
            }

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Dribbles {
                Integer attempts;
                Integer success;
                Integer past;
            }

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Fouls {
                Integer drawn;
                Integer committed;
            }

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Cards {
                Integer yellow;
                Integer red;
            }

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Penalty {
                Integer scored;
                Integer missed;
                Integer saved;
            }
        }
    }
}
