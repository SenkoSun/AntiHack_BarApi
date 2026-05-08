package com.senkosun.antihack_barapi.service;

import com.senkosun.antihack_barapi.dto.response.HistoryResponse;
import com.senkosun.antihack_barapi.dto.response.MenuResponse;
import com.senkosun.antihack_barapi.entity.Bar;
import com.senkosun.antihack_barapi.entity.User;
import com.senkosun.antihack_barapi.enums.Drink;

import java.util.List;

public interface HistoryService {
    List<HistoryResponse.DrinkItem> getOrders(User user);

}
