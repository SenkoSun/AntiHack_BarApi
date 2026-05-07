package com.senkosun.antihack_barapi.service;

import com.senkosun.antihack_barapi.dto.response.MenuResponse;
import com.senkosun.antihack_barapi.entity.Bar;
import com.senkosun.antihack_barapi.entity.User;
import com.senkosun.antihack_barapi.enums.Drink;

import java.util.List;

public interface BarService {
    Bar Menu();
    Bar Tip();

    Bar getBarByUser(User user);
    List<MenuResponse.DrinkItem> getDrinks();
    MenuResponse.DrinkItem toDrinkItem(Drink drink);
}
