package by.guz.fantasy.football.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class LineupPlayerDto {;
    private interface Number { @NotNull Integer getNumber(); }
    private interface PlayerId { @NotNull Long getPlayerId(); }
    private interface Player { @NotNull PlayerDto.Response.Default getPlayer(); }

    @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Default implements Number, PlayerId {
        Integer number;
        Long playerId;
    }

    @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Extended implements Number, Player {
        Integer number;
        PlayerDto.Response.Default player;
    }

}
