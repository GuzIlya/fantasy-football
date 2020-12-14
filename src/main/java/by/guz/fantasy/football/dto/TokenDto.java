package by.guz.fantasy.football.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public enum TokenDto {;
    private interface IssuedAt { Long getIssuedAt(); }
    private interface AccessToken { String getAccessToken(); }
    private interface AccessExpiresIn { Long getAccessExpiresIn(); }
    private interface RefreshToken { @NotNull String getRefreshToken(); }
    private interface RefreshExpiresIn { Long getRefreshExpiresIn(); }

    public enum Response {;

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Default implements IssuedAt, AccessToken, AccessExpiresIn, RefreshToken, RefreshExpiresIn {
            Long issuedAt;
            String accessToken;
            Long accessExpiresIn;
            String refreshToken;
            Long refreshExpiresIn;
        }
    }
}
