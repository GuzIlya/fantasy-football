package by.guz.fantasy.football.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public enum UserStatisticsDto {;
    private interface Id { @Positive Long getId(); }
    private interface User { @NotNull UserDto.Response.Default getUser(); }
    private interface Rank { Long getRank(); }
    private interface PrevRank { Long getPrevRank(); }
    private interface Pts { @NotNull Double getPts(); }
    private interface PrevPts { Double getPrevPts(); }
    private interface RoundParticipated { Integer getRoundParticipated(); }

    public enum Response {;

        @Getter @Setter @NoArgsConstructor
        public static class Default implements Id, User, Rank, PrevRank, Pts, PrevPts, RoundParticipated {
            Long id;
            UserDto.Response.Default user;
            Long rank;
            Long prevRank;
            Double pts;
            Double prevPts;
            Integer roundParticipated;
        }
    }
}
