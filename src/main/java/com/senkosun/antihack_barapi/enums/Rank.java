package com.senkosun.antihack_barapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Rank {
    BEGINNER("Новичок", 0),
    GUEST("Гость", 3),
    REGULAR("Постоянный", 5),
    CONNOISSEUR("Знаток", 8),
    MASTER("Мастер", 12);

    private final String displayName;
    private final int requiredUniqueDrinks;  // сколько уникальных напитков нужно выпить

    @JsonCreator
    public static Rank fromDisplayName(String name) {
        for (Rank rank : values()) {
            if (rank.displayName.equals(name)) {
                return rank;
            }
        }
        return BEGINNER;  // по умолчанию
    }

    @JsonValue
    public String toJson() {
        return displayName;
    }

    // Получить ранг по количеству уникальных напитков
    public static Rank getRankByUniqueDrinks(int uniqueDrinksCount) {
        Rank result = BEGINNER;
        for (Rank rank : values()) {
            if (uniqueDrinksCount >= rank.requiredUniqueDrinks) {
                result = rank;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return displayName;
    }
}