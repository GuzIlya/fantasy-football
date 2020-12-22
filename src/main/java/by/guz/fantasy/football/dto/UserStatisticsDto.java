package by.guz.fantasy.football.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public enum UserStatisticsDto {;
    private interface User { @NotNull UserDto.Response.Default getUser(); }
    private interface Rank { Long getRank(); }
    private interface PrevRank { Long getPrevRank(); }
    private interface Pts { @NotNull BigDecimal getPts(); }
    private interface PrevPts { BigDecimal getPrevPts(); }
    private interface RoundsParticipated { Integer getRoundsParticipated(); }

    public enum Response {;

        @Getter @Setter @NoArgsConstructor
        public static class Default implements User, Rank, PrevRank, Pts, PrevPts, RoundsParticipated {
            UserDto.Response.Default user;
            Long rank;
            Long prevRank;
            BigDecimal pts;
            BigDecimal prevPts;
            Integer roundsParticipated;
        }
    }
}
