package com.senkosun.antihack_barapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Ingredient {
    VODKA("водка"),
    RUM ("ром"),
    TEQUILA("текила"),
    WHISKEY("виски"),
    GIN("джин"),
    COLA("кола"),
    JUICE("сок"),
    TONIC("тоник"),
    ICE("лёд"),
    MILK("молоко");

    private final String displayName;

    Ingredient(String displayName) {
        this.displayName = displayName;
    }

    @JsonCreator
    public static Ingredient fromDisplayName(String name) {
        for (Ingredient ingredient : values()) {
            if (ingredient.displayName.equals(name)) {
                return ingredient;
            }
        }
        return null;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}