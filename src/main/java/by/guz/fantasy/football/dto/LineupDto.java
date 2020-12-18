package by.guz.fantasy.football.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public enum LineupDto {;
    private interface Id { @Positive Long getId(); }
    private interface UserId { @NotNull Long getUserId(); }
    private interface RoundId { @NotNull Long getRoundId(); }

    public enum Response {;
        @Getter
        @Setter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Default implements Id, UserId, RoundId {
            Long id;
            Long userId;
            Long roundId;
        }
    }
}
