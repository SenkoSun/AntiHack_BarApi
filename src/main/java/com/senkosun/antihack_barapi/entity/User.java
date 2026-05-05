package com.senkosun.antihack_barapi.entity;

import com.senkosun.antihack_barapi.enums.Rank;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 32)
    private String token;

    @Column(nullable = false)
    @Builder.Default
    private Integer balance = 100;

    @Column(nullable = false)
    @Builder.Default
    private String rang = Rank.BEGINNER.getDisplayName();


    public boolean deductBalance(int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}