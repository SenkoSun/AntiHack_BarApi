package com.senkosun.antihack_barapi.service;

import com.senkosun.antihack_barapi.entity.Bar;
import com.senkosun.antihack_barapi.entity.User;

public interface BarService {
    Bar Menu();
    Bar Tip();

    Bar getBarByUser(User user);
}
