package by.guz.fantasy.football.dto;

import by.guz.fantasy.football.entity.enums.PlayerPositionEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

public enum PlayerDto {;
    private interface Id { @Positive Long getId(); }
    private interface ExternalId { @Positive Long getExternalId(); }
    private interface Name { @NotNull String getName(); }
    private interface Firstname { @NotNull String getFirstname(); }
    private interface Lastname { @NotNull String getLastname(); }
    private interface Birthdate { Date getBirthdate(); }
    private interface Nationality { @NotNull String getNationality(); }
    private interface Cost { @NotNull Double getCost(); }
    private interface Height { String getHeight(); }
    private interface Weight { String getWeight(); }
    private interface Injured { @NotNull Boolean getInjured(); }
    private interface Photo { String getPhoto(); }
    private interface TeamId { Long getTeamId(); }
    private interface Team { TeamDto.Response.Default getTeam(); }
    private interface Position { PlayerPositionEntity getPosition(); }


    public enum Response {;
        @Getter @Setter @NoArgsConstructor
        public static class Default implements Id, Name, Firstname, Lastname, Birthdate,
                Nationality, Cost, Height, Weight, Injured, Photo, Team, Position {
            Long id;
            String name;
            String firstname;
            String lastname;
            Date birthdate;
            String nationality;
            Double cost;
            String height;
            String weight;
            Boolean injured;
            String photo;
            PlayerPositionEntity position;
            TeamDto.Response.Default team;
        }
    }

    public enum External {;

        @Getter
        @Setter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DefaultList {
            List<DefaultCover> response;
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DefaultCover {
            Default player;
            List<Statistics> statistics;

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Statistics {
                Team team;
                Games games;
            }

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Team {
                Long id;
            }

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Games {
                String position;
            }
        }

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Default {
            Long id;
            String name;
            String firstname;
            String lastname;
            Birth birth;
            String nationality;
            String height;
            String weight;
            Boolean injured;
            String photo;

            @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Birth {
                Date date;
            }
        }

    }
}
