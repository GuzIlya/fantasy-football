package by.guz.fantasy.football.dto;

import by.guz.fantasy.football.entity.enums.RoundStatusEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;

public enum RoundDto {;
    private interface Id { @Positive Long getId(); }
    private interface Name { @NotBlank String getName(); }
    private interface Status { @NotNull RoundStatusEntity getStatus(); }

    public enum Response {;

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Default implements Id, Name, Status {
            Long id;
            String name;
            RoundStatusEntity status;
        }
    }

    public enum External {;

        @Getter @Setter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Default {
            List<String> response;
        }
    }
}
