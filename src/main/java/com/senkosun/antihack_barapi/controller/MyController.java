package com.senkosun.antihack_barapi.controller;
//import com.senkosun.antihack_barapi.service.AuthService;
//import com.senkosun.antihack_barapi.service.BarService;
//import com.senkosun.antihack_barapi.service.UserService;
//import com.senkosun.antihack_barapi.service.HistoryService;

import com.senkosun.antihack_barapi.dto.responce.RegisterResponse;
import com.senkosun.antihack_barapi.entity.User;
import com.senkosun.antihack_barapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MyController {

    private final AuthService authService;

//    @Autowired
//    private BarService barService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
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

    // Сброс
//    @PostMapping("/reset")
//    public ResetResponse reset(@RequestHeader("Authorization") String auth) {
//        return userService.reset(auth);
//    }

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

    // Баланс
//    @GetMapping("/balance")
//    public BalanceResponse getBalance(@RequestHeader("Authorization") String auth) {
//        return userService.getBalance(auth);
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