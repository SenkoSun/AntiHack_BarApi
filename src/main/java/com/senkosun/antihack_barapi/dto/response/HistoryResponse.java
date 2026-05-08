package com.senkosun.antihack_barapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponse {
    private String status;
    private List<DrinkItem> orders;
    private Integer balance;
    private String mood_level;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DrinkItem {
        private String name;
        private Integer price;
        private String method;
    }
}