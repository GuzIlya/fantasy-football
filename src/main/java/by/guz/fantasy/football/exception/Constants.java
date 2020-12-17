package by.guz.fantasy.football.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String USERNAME_OR_PASSWORD_BAD_REQUEST = "Incorrect username or password.";

    public static final String REFRESH_TOKEN_UNAUTHORIZED = "Invalid refresh token.";

    public static final String USER_NOT_FOUND = "User could not be found.";
    public static final String USER_USERNAME_NOT_FOUND = "User with such username not found.";

    public static final String USER_EMAIL_CONFLICT = "User with such email already exists.";
    public static final String USER_USERNAME_CONFLICT = "User with such username already exists.";
    public static final String EXTERNAL_API_UNABLE_CONFLICT = "Processing this endpoint requires connection to external API, which is disabled in server.";
}
