package com.senkosun.antihack_barapi.service;

import com.senkosun.antihack_barapi.dto.response.MenuResponse;
import com.senkosun.antihack_barapi.entity.Bar;
import com.senkosun.antihack_barapi.entity.User;
import com.senkosun.antihack_barapi.enums.Drink;
import com.senkosun.antihack_barapi.enums.Ingredient;
import com.senkosun.antihack_barapi.repository.BarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<MenuResponse.DrinkItem> getDrinks() {
        return Arrays.stream(Drink.values())
                .map(this::toDrinkItem)
                .collect(Collectors.toList());
    }

    @Override
    public MenuResponse.DrinkItem toDrinkItem(Drink drink) {
        return MenuResponse.DrinkItem.builder()
                .name(drink.getDisplayName())
                .price(drink.getPrice())
                .ingredients(
                        drink.getIngredients().stream()
                                .map(Ingredient::getDisplayName)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
