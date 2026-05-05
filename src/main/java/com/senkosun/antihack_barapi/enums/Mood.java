package com.senkosun.antihack_barapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Mood {
    HOSTILE("hostile", 0),
    GRUMPY("grumpy", 30),
    NORMAL("normal", 60),
    FRIENDLY("friendly", 70),
    GENEROUS("generous", 100);

    private final String displayName;
    private final int threshold;  // минимальное значение для этого настроения

    @JsonCreator
    public static Mood fromDisplayName(String name) {
        for (Mood mood : values()) {
            if (mood.displayName.equals(name)) {
                return mood;
            }
        }
        return NORMAL;
    }

    @JsonValue
    public String toJson() {
        return displayName;
    }

    // Получить настроение по числовому значению
    public static Mood fromValue(int moodValue) {
        Mood result = HOSTILE;
        for (Mood mood : values()) {
            if (moodValue >= mood.threshold) {
                result = mood;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return displayName;
    }
}