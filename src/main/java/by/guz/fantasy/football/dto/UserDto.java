package by.guz.fantasy.football.dto;

import by.guz.fantasy.football.entity.enums.UserRoleEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

public enum UserDto {;
    private interface Id { @Positive Long getId(); }
    private interface Username { @NotBlank String getUsername(); }
    private interface Password { @NotBlank String getPassword(); }
    private interface Email { @NotNull String getEmail(); }
    private interface Cash { @NotNull Double getCash(); }
    private interface Role { @NotNull UserRoleEntity getRole(); }
    private interface CreatedAt { @NotNull Instant getCreatedAt(); }
    private interface UpdatedAt { Instant getUpdatedAt(); }

    public enum Response {;

        @Getter @Setter @NoArgsConstructor
        public static class Default implements Id, Username, Email, Cash, Role, CreatedAt, UpdatedAt {
            Long id;
            String username;
            String email;
            Double cash;
            UserRoleEntity role;
            Instant createdAt;
            Instant updatedAt;
        }
    }

    public enum Request {;

        @Getter @Setter @NoArgsConstructor
        public static class SignUp implements Username, Password, Email {
            String username;
            String password;
            String email;
        }

        @Getter @Setter @NoArgsConstructor
        public static class Login implements Username, Password {
            String username;
            String password;
        }
    }

}

