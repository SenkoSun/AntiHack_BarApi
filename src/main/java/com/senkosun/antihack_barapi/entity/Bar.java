package com.senkosun.antihack_barapi.entity;

import com.senkosun.antihack_barapi.enums.Mood;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bars")
public class Bar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "mood_level", nullable = false)
    @Builder.Default
    private String moodLevel = Mood.NORMAL.getDisplayName();

    @Column(name = "bar_closed", nullable = false)
    @Builder.Default
    private Boolean barClosed = false;

    @Column(name = "total_orders", nullable = false)
    @Builder.Default
    private Integer totalOrders = 0;

    @Column(name = "unique_drinks_count", nullable = false)
    @Builder.Default
    private Integer uniqueDrinksCount = 0;

    @Column(name = "favorite_drink")
    private String favoriteDrink;

    // Методы для обновления статистики
    public void incrementTotalOrders() {
        this.totalOrders++;
    }

    public void updateUniqueDrinks(String drinkName) {
        this.uniqueDrinksCount++;
    }
}