package com.senkosun.antihack_barapi.service;

import com.senkosun.antihack_barapi.dto.response.HistoryResponse;
import com.senkosun.antihack_barapi.entity.Order;
import com.senkosun.antihack_barapi.entity.User;
import com.senkosun.antihack_barapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class  HistoryServiceImpl implements HistoryService {

    private final OrderRepository orderRepository;

    @Override
    public List<HistoryResponse.DrinkItem> getOrders(User user) {
        // 1. Находим все заказы пользователя, сортируем от новых к старым
        List<Order> orders = orderRepository.findByUserIdOrderById(user.getId());

        // 2. Преобразуем Order в DrinkItem (DTO)
        return orders.stream()
                .map(this::convertToDrinkItem)
                .collect(Collectors.toList());
    }

    // Конвертация Order → DrinkItem
    private HistoryResponse.DrinkItem convertToDrinkItem(Order order) {
        return HistoryResponse.DrinkItem.builder()
                .name(order.getDrinkName())
                .price(order.getPrice())
                .method(order.getMethod())  // "order" или "mix"
                .build();
    }
}
