package com.senkosun.antihack_barapi.entity;

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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "drink_name", nullable = false)
    private String drinkName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String method;  // "order" или "mix"


    // Хелпер для создания заказа
    public static Order createOrder(User user, String drinkName, Integer price, String method) {
        return Order.builder()
                .user(user)
                .drinkName(drinkName)
                .price(price)
                .method(method)
                .build();
    }
}