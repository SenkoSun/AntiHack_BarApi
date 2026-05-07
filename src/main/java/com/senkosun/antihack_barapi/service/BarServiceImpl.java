package com.senkosun.antihack_barapi.service;

import com.senkosun.antihack_barapi.entity.Bar;
import com.senkosun.antihack_barapi.entity.User;
import com.senkosun.antihack_barapi.repository.BarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarServiceImpl implements BarService{

    private final BarRepository barRepository;

    @Override
    public Bar Menu() {
        return null;
    }

    @Override
    public Bar Tip() {
        return null;
    }

    @Override
    public Bar getBarByUser(User user) {
        return barRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Бар не найден для пользователя id=" + user.getId()));
    }
}
