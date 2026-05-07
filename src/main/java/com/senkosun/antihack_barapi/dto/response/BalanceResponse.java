package com.senkosun.antihack_barapi.dto.response;

import com.senkosun.antihack_barapi.enums.Mood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse {
    private String status;
    private Integer balance;
    private String moodLevel;
}