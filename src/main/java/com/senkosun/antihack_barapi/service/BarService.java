package com.senkosun.antihack_barapi.service;

import com.senkosun.antihack_barapi.entity.Bar;
import com.senkosun.antihack_barapi.entity.User;
import com.senkosun.antihack_barapi.enums.Mood;

public interface BarService {
    Bar Menu();
    Bar Tip();

    String getMoodLevel(User user);
    Bar getBarByUser(User user);
}
