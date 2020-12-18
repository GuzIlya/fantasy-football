package by.guz.fantasy.football.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoundStatusEntity {

    UPCOMING("UPCOMING"),
    OPEN("OPEN"),
    CLOSED("CLOSED"),
    FINISHED("FINISHED"),
    PROCESSED("PROCESSED");

    private String value;

    RoundStatusEntity(final String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static RoundStatusEntity fromValue(final String text) {
        for (var b : RoundStatusEntity.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
