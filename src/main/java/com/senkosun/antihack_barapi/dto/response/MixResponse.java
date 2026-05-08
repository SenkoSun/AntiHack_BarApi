package com.senkosun.antihack_barapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MixResponse {
    private String status;
    private String drink;
    private Integer price;
    private Integer balance;
    private String mood_level;
}