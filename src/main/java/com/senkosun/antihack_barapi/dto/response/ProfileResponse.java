package com.senkosun.antihack_barapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private String status;
    private String id;
    private String rank;
    private Integer total_orders;
    private Integer unique_drinks;
    private String favorite_drink;
    private boolean bar_closed;
}