package com.senkosun.antihack_barapi.repository;

import com.senkosun.antihack_barapi.entity.Bar;
import com.senkosun.antihack_barapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarRepository extends JpaRepository<Bar, Integer> {

    // Найти бар по пользователю
    Optional<Bar> findByUser(User user);

    // Найти бар по ID пользователя
    Optional<Bar> findByUserId(Integer userId);

    // Проверить, существует ли бар у пользователя
    boolean existsByUserId(Integer userId);


}