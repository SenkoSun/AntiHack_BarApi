package com.senkosun.antihack_barapi.repository;

import com.senkosun.antihack_barapi.entity.Order;
import com.senkosun.antihack_barapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Найти все заказы пользователя (по пользователю)
    List<Order> findByUser(User user);

    // Найти все заказы пользователя (по ID пользователя)
    List<Order> findByUserId(Integer userId);

    // Найти заказы пользователя, отсортированные по ID
    List<Order> findByUserIdOrderById(Integer userId);

    // Подсчитать количество заказов пользователя
    int countByUserId(Integer userId);


    // Подсчитать количество уникальных напитков пользователя
    @Query("SELECT COUNT(DISTINCT o.drinkName) FROM Order o WHERE o.user.id = :userId")
    int countUniqueDrinksByUserId(@Param("userId") Integer userId);

    // Найти любимый напиток пользователя (самый частый)
    @Query(value = "SELECT drink_name FROM orders WHERE user_id = :userId " +
            "GROUP BY drink_name HAVING COUNT(*) >= 3 ORDER BY COUNT(*) DESC LIMIT 1",
            nativeQuery = true)
    String findFavoriteDrinkByUserId(@Param("userId") Integer userId);
}