package by.guz.fantasy.football.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String USERNAME_OR_PASSWORD_BAD_REQUEST = "Incorrect username or password.";

    public static final String REFRESH_TOKEN_UNAUTHORIZED = "Invalid refresh token.";

    public static final String USER_NOT_FOUND = "User could not be found.";
    public static final String PLAYER_NOT_FOUND = "Player could not be found.";
    public static final String USER_USERNAME_NOT_FOUND = "User with such username not found.";
    public static final String USER_PLAYER_NOT_FOUND = "User don't have such Player.";
    public static final String ROUND_NOT_FOUND = "Round could not be found.";
    public static final String TEAM_NOT_FOUND = "Team could not be found.";
    public static final String LINEUP_NOT_FOUND = "Lineup could not be found.";
    public static final String FIXTURE_NOT_FOUND = "Fixture could not be found.";

    public static final String USER_EMAIL_CONFLICT = "User with such email already exists.";
    public static final String USER_USERNAME_CONFLICT = "User with such username already exists.";
    public static final String EXTERNAL_API_UNABLE_CONFLICT = "Processing this endpoint requires connection to external API, which is disabled in server.";
    public static final String NOT_ENOUGH_CASH_CONFLICT = "Not enough cash to purchase.";
    public static final String ROUND_NOT_OPEN_CONFLICT = "Round is not opened, lineup modifying rejected.";
    public static final String PLAYER_DONT_PURCHASED_CONFLICT = "User dont have player with such id: ";
}
