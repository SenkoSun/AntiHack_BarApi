package com.senkosun.antihack_barapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuResponse {
    private String status;

    private String error;

    private List<DrinkItem> drinks;
    private Integer balance;
    private String mood_level;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DrinkItem {
        private String name;
        private Integer price;
        private List<String> ingredients;
    }
}