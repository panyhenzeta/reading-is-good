package com.getir.readingisgood.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyStatisticsDTO {
    private String month;
    private int totalOrder;
    private Long totalBookCount;
    private Double totalPurchasedAmount;
}
