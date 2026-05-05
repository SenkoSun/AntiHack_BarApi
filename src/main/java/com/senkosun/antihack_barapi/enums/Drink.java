package com.senkosun.antihack_barapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Drink {
    CUBA_LIBRE("Куба Либре", Set.of(Ingredient.COLA, Ingredient.ICE, Ingredient.RUM), 12),
    SCREWDRIVER("Отвёртка", Set.of(Ingredient.VODKA, Ingredient.JUICE), 12),
    GIN_TONIC("Джин-тоник", Set.of(Ingredient.GIN, Ingredient.ICE, Ingredient.TONIC), 14),
    WHISKY_COLA("Виски-кола", Set.of(Ingredient.WHISKEY, Ingredient.COLA), 14),
    TEQUILA_SUNRISE("Текила-санрайз", Set.of(Ingredient.JUICE, Ingredient.TEQUILA), 15),
    RUSSIAN("Русский", Set.of(Ingredient.VODKA, Ingredient.ICE), 10),
    WHITE_RUSSIAN("Белый русский", Set.of(Ingredient.VODKA, Ingredient.ICE, Ingredient.MILK), 13),
    LONG_ISLAND("Лонг-Айленд", Set.of(
            Ingredient.VODKA, Ingredient.GIN, Ingredient.COLA,
            Ingredient.RUM, Ingredient.TEQUILA), 20);

    private final String displayName;
    private final Set<Ingredient> ingredients;
    private final int price;

    @JsonCreator
    public static Drink fromDisplayName(String name) {
        for (Drink drink : values()) {
            if (drink.displayName.equals(name)) {
                return drink;
            }
        }
        return null;
    }

    @JsonValue
    public String toJson() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}