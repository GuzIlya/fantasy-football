package by.guz.fantasy.football.repository.custom;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Filter {
    private String field;
    private List<String> fields;
    private QueryOperator operator;
    private String value;
}
