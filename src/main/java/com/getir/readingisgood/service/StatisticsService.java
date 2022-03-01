package com.getir.readingisgood.service;

import com.getir.readingisgood.model.CustomerOrder;
import com.getir.readingisgood.model.dto.MonthlyStatisticsDTO;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Statistics service that statistic operations.
 */
@Service
public class StatisticsService {
    private final CustomerOrderService customerOrderService;

    public StatisticsService(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    /**
     * method that give monthly statistics.
     *
     * @return success response with list of monthly statistics.
     */
    public List<MonthlyStatisticsDTO> getMonthlyStatistics(){
        Map<Integer, List<CustomerOrder>> grouppedList = customerOrderService.findAll()
                .stream()
                .collect(Collectors.groupingBy(customerOrder -> Date.from(customerOrder.getOrderDate()).getMonth()));

        List<MonthlyStatisticsDTO> monthlyStatisticsDTOS = new ArrayList<>();
        grouppedList.forEach((month, customerOrders) -> {
            AtomicReference<Long> totalBook = new AtomicReference<>(0L);
            AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
            customerOrders.forEach(customerOrder -> {
                totalBook.updateAndGet(v -> v + customerOrder.getBookList().stream().mapToLong(value -> value.getQuantity()).sum());
                totalPrice.updateAndGet(v -> v + customerOrder.getTotalPrice());
            });
            MonthlyStatisticsDTO monthlyStatisticsDTO =
                    new MonthlyStatisticsDTO(month.toString(), customerOrders.size(), totalBook.get(), totalPrice.get());
            monthlyStatisticsDTOS.add(monthlyStatisticsDTO);
        });
        return monthlyStatisticsDTOS;
    }
}
