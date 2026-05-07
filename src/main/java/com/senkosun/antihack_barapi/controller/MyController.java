package com.senkosun.antihack_barapi.controller;
import com.senkosun.antihack_barapi.enums.Mood;
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
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // 1. Получаем пользователя по токену из заголовка
        User user = authService.getAuthenticatedUser(authHeader);

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
    public BalanceResponse getBalance(@RequestHeader("Authorization") String authHeader) {
        User user = authService.getAuthenticatedUser(authHeader);
        if (user == null) {
            return BalanceResponse.builder().status("error").build();
        }

        // 2. Получаем настроение бармена
        String moodLevel = barService.getMoodLevel(user);

        // 3. Формируем ответ
        BalanceResponse response = BalanceResponse.builder()
                .status("ok")
                .balance(user.getBalance())
                .moodLevel(moodLevel)
                .build();

        log.info("Пользователь {} запросил баланс: {}", user.getId(), user.getBalance());

        return response;
    }
    // Меню
//    @GetMapping("/menu")
//    public MenuResponse getMenu(@RequestHeader("Authorization") String auth,
//                                @RequestHeader("X-Time") String time) {
//        return barService.getMenu(auth, time);
//    }

    // Заказ
//    @PostMapping("/order")
//    public OrderResponse order(@RequestHeader("Authorization") String auth,
//                               @RequestHeader("X-Time") String time,
//                               @RequestBody OrderRequest request) {
//        return barService.order(auth, time, request);
//    }

    // Микс
//    @PostMapping("/mix")
//    public MixResponse mix(@RequestHeader("Authorization") String auth,
//                           @RequestHeader("X-Time") String time,
//                           @RequestBody MixRequest request) {
//        return barService.mix(auth, time, request);
//    }


    // Чаевые
//    @PostMapping("/tip")
//    public TipResponse tip(@RequestHeader("Authorization") String auth,
//                           @RequestBody TipRequest request) {
//        return barService.tip(auth, request);
//    }

    // История
//    @GetMapping("/history")
//    public HistoryResponse getHistory(@RequestHeader("Authorization") String auth) {
//        return historyService.getHistory(auth);
//    }

    // Профиль
//    @GetMapping("/profile")
//    public ProfileResponse getProfile(@RequestHeader("Authorization") String auth) {
//        return userService.getProfile(auth);
//    }
}