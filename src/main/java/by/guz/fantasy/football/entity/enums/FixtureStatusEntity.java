package by.guz.fantasy.football.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FixtureStatusEntity {

    TBD("UNKNOWN"),
    NS("NS"),
    FH("1H"),
    HT("HT"),
    SH("2H"),
    ET("ET"),
    P("P"),
    FT("FT"),
    AET("AET"),
    PEN("PEN"),
    BT("BT"),
    SUSP("SUSP"),
    INT("INT"),
    PST("PST"),
    CANC("CANC"),
    ABD("ABD"),
    AWD("AWD"),
    WO("WO");

    private String value;

    FixtureStatusEntity(final String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static FixtureStatusEntity fromValue(final String text) {
        for (var b : FixtureStatusEntity.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
