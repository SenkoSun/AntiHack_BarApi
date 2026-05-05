package com.senkosun.antihack_barapi.repository;

import com.senkosun.antihack_barapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Поиск пользователя по токену (свой метод)
    Optional<User> findByToken(String token);

    // Проверка существования токена
    boolean existsByToken(String token);

}