package com.senkosun.antihack_barapi.service;

import com.senkosun.antihack_barapi.entity.Bar;
import com.senkosun.antihack_barapi.entity.User;
import com.senkosun.antihack_barapi.enums.Mood;
import com.senkosun.antihack_barapi.enums.Rank;
import com.senkosun.antihack_barapi.repository.BarRepository;
import com.senkosun.antihack_barapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final BarRepository barRepository;

    @Transactional
    @Override
    public User registerUser() {

        String token = generateToken();
        User user = User
                .builder()
                .token(token)
                .build();
        userRepository.save(user);

        Bar bar = Bar.builder()
                .user(user)
                .moodLevel(Mood.NORMAL.getDisplayName())
                .barClosed(false)
                .totalOrders(0)
                .uniqueDrinksCount(0)
                .favoriteDrink(null)
                .build();
        barRepository.save(bar);

        log.info("Новый пользователь зарегистрирован: id={}, token={}",
                user.getId(), user.getToken());
        return user;
    }

    @Transactional
    @Override
    public void resetUser(User user) {

        user.setBalance(100);
        user.setRang(Rank.BEGINNER.getDisplayName());
        userRepository.save(user);

        Bar bar = barRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Бар не найден для пользователя id=" + user.getId()));

        bar.setMoodLevel(Mood.NORMAL.getDisplayName());
        bar.setBarClosed(false);
        bar.setTotalOrders(0);
        bar.setUniqueDrinksCount(0);
        bar.setFavoriteDrink(null);
        barRepository.save(bar);

        log.info("Пользователь {} сбросил аккаунт", user.getId());

    }

    @Override
    public User getAuthenticatedUser(String authHeader) {
        // Проверяем, что заголовок есть и начинается с "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        // Извлекаем токен (убираем "Bearer ")
        String token = authHeader.substring(7);

        // Ищем пользователя по токену
        return userRepository.findByToken(token).orElse(null);
    }

    @Override
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
