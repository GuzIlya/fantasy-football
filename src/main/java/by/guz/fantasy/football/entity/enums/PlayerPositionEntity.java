package by.guz.fantasy.football.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PlayerPositionEntity {

    UNKNOWN("UNKNOWN"),
    GOALKEEPER("GOALKEEPER"),
    DEFENDER("DEFENDER"),
    MIDFIELDER("MIDFIELDER"),
    ATTACKER("ATTACKER");

    private String value;

    PlayerPositionEntity(final String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static PlayerPositionEntity fromValue(final String text) {
        for (var b : PlayerPositionEntity.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
