package by.guz.fantasy.football.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public enum UserStatisticsDto {;
    private interface User { @NotNull UserDto.Response.Default getUser(); }
    private interface Rank { Long getRank(); }
    private interface PrevRank { Long getPrevRank(); }
    private interface Pts { @NotNull Double getPts(); }
    private interface PrevPts { Double getPrevPts(); }
    private interface RoundParticipated { Integer getRoundParticipated(); }

    public enum Response {;

        @Getter @Setter @NoArgsConstructor
        public static class Default implements User, Rank, PrevRank, Pts, PrevPts, RoundParticipated {
            UserDto.Response.Default user;
            Long rank;
            Long prevRank;
            Double pts;
            Double prevPts;
            Integer roundParticipated;
        }
    }
}
