package com.senkosun.antihack_barapi.service;

import com.senkosun.antihack_barapi.dto.response.MenuResponse;
import com.senkosun.antihack_barapi.entity.Bar;
import com.senkosun.antihack_barapi.entity.Order;
import com.senkosun.antihack_barapi.entity.User;
import com.senkosun.antihack_barapi.enums.Drink;
import com.senkosun.antihack_barapi.enums.Ingredient;
import com.senkosun.antihack_barapi.enums.Mood;
import com.senkosun.antihack_barapi.repository.BarRepository;
import com.senkosun.antihack_barapi.repository.OrderRepository;
import com.senkosun.antihack_barapi.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

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

    @Override
    public Bar getTip(User user, Integer tip) {
        Bar bar = getBarByUser(user);
        bar.moodInt = Math.min(bar.moodInt + tip, 100);
        bar.setMoodLevel(Mood.fromValue(bar.moodInt).getDisplayName());
        user.setBalance(user.getBalance() - tip);

        barRepository.save(bar);
        userRepository.save(user);

        return bar;
    }

    @Override
    public Bar makeOrder(User user, Drink drink, Boolean isOrder) {
        Bar bar = getBarByUser(user);
        if (drink.getDisplayName().equals("Лонг-Айленд")) {
            bar.moodInt = Math.min(bar.moodInt + 10, 100);
        } else if (drink.getDisplayName().equals("Русский")) {
            bar.moodInt = Math.max(bar.moodInt - 10, 0);
        } else {
            bar.moodInt = Math.max(bar.moodInt - 5, 0);
        }

        int discount = 0;
        if (!isOrder) discount += 2;
        bar.setMoodLevel(Mood.fromValue(bar.moodInt).getDisplayName());

        user.setBalance(user.getBalance() - drink.getPrice() + discount);

        Order order = Order.builder()
                .user(user)
                .drinkName(drink.getDisplayName())
                .price(drink.getPrice() - discount)
                .method(isOrder?"order":"mix")
                .build();

        orderRepository.save(order);
        barRepository.save(bar);
        userRepository.save(user);

        return bar;
    }

}
