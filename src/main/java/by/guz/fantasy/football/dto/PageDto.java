package by.guz.fantasy.football.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageDto {
    private final Integer current;
    private final Integer total;
}
