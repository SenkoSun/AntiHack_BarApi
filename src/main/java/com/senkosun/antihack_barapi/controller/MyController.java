package com.senkosun.antihack_barapi.controller;
import com.senkosun.antihack_barapi.dto.request.MixRequest;
import com.senkosun.antihack_barapi.dto.request.OrderRequest;
import com.senkosun.antihack_barapi.dto.request.TipRequest;
import com.senkosun.antihack_barapi.entity.Bar;
import com.senkosun.antihack_barapi.entity.Order;
import com.senkosun.antihack_barapi.enums.Drink;
import com.senkosun.antihack_barapi.enums.Ingredient;
import com.senkosun.antihack_barapi.service.*;
//import com.senkosun.antihack_barapi.service.BarService;
//import com.senkosun.antihack_barapi.service.HistoryService;

import com.senkosun.antihack_barapi.dto.response.*;
import com.senkosun.antihack_barapi.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MyController {

    private final AuthService authService;

    private final BarService barService;
//
//    private final UserService userService;
//
//    private HistoryService historyService;

//     Регистрация
    @PostMapping("/register")
    public RegisterResponse register() {
        User user = authService.registerUser();
        return RegisterResponse.builder()
                .status("ok")
                .id("BAR-" + user.getId())
                .token(user.getToken())
                .build();
    }

    //     Сброс
    @PostMapping("/reset")
    public ResetResponse reset(
            @RequestHeader(value = "Authorization", required = false) String auth) {

        // 1. Получаем пользователя по токену из заголовка
        User user = authService.getAuthenticatedUser(auth);

        // 2. Проверяем авторизацию
        if (user == null) {
            return ResetResponse.builder().status("error").build();
        }

        // 3. Вызываем сброс
        authService.resetUser(user);

        // 4. Возвращаем ответ
        return ResetResponse.builder().status("ok").build();
    }

    // Баланс
    @GetMapping("/balance")
    public BalanceResponse getBalance(@RequestHeader("Authorization") String auth) {
        User user = authService.getAuthenticatedUser(auth);
        if (user == null) {
            return BalanceResponse.builder().status("error").build();
        }

        Bar bar = barService.getBarByUser(user);

        // 3. Формируем ответ
        BalanceResponse response = BalanceResponse.builder()
                .status("ok")
                .balance(user.getBalance())
                .mood_level(bar.getMoodLevel())
                .build();

        log.info("Пользователь {} запросил баланс: {}", user.getId(), user.getBalance());

        return response;
    }

//     Профиль
    @GetMapping("/profile")
    public ProfileResponse getProfile(@RequestHeader("Authorization") String auth) {
        User user = authService.getAuthenticatedUser(auth);
        if (user == null) {
            return ProfileResponse.builder().status("error").build();
        }

        Bar bar = barService.getBarByUser(user);

        ProfileResponse response = ProfileResponse.builder()
                .status("ok")
                .id("BAR-" + user.getId())
                .rank(user.getRang())
                .total_orders(bar.getTotalOrders())
                .unique_drinks(bar.getUniqueDrinksCount())
                .favorite_drink(bar.getFavoriteDrink())
                .bar_closed(bar.getBarClosed())
                .build();

        log.info("Пользователь {} запросил профиль", user.getId());
        return response;
    }

    // Меню
    @GetMapping("/menu")
    public MenuResponse getMenu(@RequestHeader("Authorization") String auth,
                                @RequestHeader("X-Time") String time) {
        User user = authService.getAuthenticatedUser(auth);
        if (user == null) {
            return MenuResponse.builder().status("error").build();
        }
        Bar bar = barService.getBarByUser(user);
        List<MenuResponse.DrinkItem> drinks = barService.getDrinks();
        MenuResponse response = MenuResponse.builder()
                .status("ok")
                .drinks(drinks)
                .balance(user.getBalance())
                .mood_level(bar.getMoodLevel())
                .build();
        log.info("Пользователь {} запросил меню", user.getId());
        return response;

    }


    // Чаевые
    @PostMapping("/tip")
    public TipResponse tip(@RequestHeader("Authorization") String auth,
                           @RequestBody TipRequest request) {
        User user = authService.getAuthenticatedUser(auth);
        if (user == null) {
            return TipResponse.builder().status("error").build();
        }

        int tipAmount = request.getAmount();
        if (tipAmount <= 0) {
            return TipResponse.builder().status("error").build();
        }
        if (user.getBalance() < tipAmount) {
            return TipResponse.builder().status("error").build();
        }

        Bar updatedBar = barService.getTip(user, tipAmount);

        TipResponse response = TipResponse.builder()
                .status("ok")
                .tip(tipAmount)
                .balance(user.getBalance())
                .mood_level(updatedBar.getMoodLevel())
                .build();

        log.info("Пользователь {} дал чаевые {}", user.getId(), tipAmount);

        return response;
    }


//     Заказ
    @PostMapping("/order")
    public OrderResponse order(@RequestHeader("Authorization") String auth,
                               @RequestHeader("X-Time") String time,
                               @RequestBody OrderRequest request) {
        User user = authService.getAuthenticatedUser(auth);

        //unauthorization
        if (user == null) {
            return OrderResponse.builder().status("error").build();
        }

        String nameDrink = request.getName();
        Drink drink = Drink.fromDisplayName(nameDrink);

        //unknown_drink
        if (drink == null) {
            return OrderResponse.builder().status("error").build();
        }

        //insufficient_funds
        if (user.getBalance() < drink.getPrice()) {
            return OrderResponse.builder().status("error").build();
        }

        Bar updatedBar = barService.makeOrder(user, drink, true);

        OrderResponse response = OrderResponse.builder()
                .status("ok")
                .drink(nameDrink)
                .price(drink.getPrice())
                .balance(user.getBalance())
                .mood_level(updatedBar.getMoodLevel())
                .build();

        log.info("Пользователь {} сделал заказ {}", user.getId(), nameDrink);

        return  response;


    }


    // Микс
    @PostMapping("/mix")
    public MixResponse mix(@RequestHeader("Authorization") String auth,
                           @RequestHeader("X-Time") String time,
                           @RequestBody MixRequest request) {
        User user = authService.getAuthenticatedUser(auth);

        //unauthorization
        if (user == null) {
            return MixResponse.builder().status("error").build();
        }

        Set<Ingredient> ingredients = request.getIngredients().stream()
                .map(Ingredient::fromDisplayName)
                .collect(Collectors.toSet());

        //unknown_ingredients
        if (ingredients.size() != request.getIngredients().size()) {
            return MixResponse.builder().status("error").build();
        }

        Drink drink = findDrinkByIngredients(ingredients);

        //unknown_drink
        if (drink == null) {
            return MixResponse.builder().status("error").build();
        }

        //insufficient_funds
        if (user.getBalance() < drink.getPrice()) {
            return MixResponse.builder().status("error").build();
        }

        Bar updatedBar = barService.makeOrder(user, drink, false);

        MixResponse response = MixResponse.builder()
                .status("ok")
                .drink(drink.getDisplayName())
                .price(drink.getPrice())
                .balance(user.getBalance())
                .mood_level(updatedBar.getMoodLevel())
                .build();

        log.info("Пользователь {} сделал микс {}", user.getId(), ingredients);

        return  response;
    }

    private Drink findDrinkByIngredients(Set<Ingredient> ingredients) {
        for (Drink drink : Drink.values()) {
            if (drink.getIngredients().equals(ingredients)) {
                return drink;
            }
        }
        return null;
    }

    // История
//    @GetMapping("/history")
//    public HistoryResponse getHistory(@RequestHeader("Authorization") String auth) {
//        return historyService.getHistory(auth);
//    }
}