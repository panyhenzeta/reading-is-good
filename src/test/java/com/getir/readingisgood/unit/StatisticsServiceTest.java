package com.getir.readingisgood.unit;

import com.getir.readingisgood.model.CustomerOrder;
import com.getir.readingisgood.model.OrderBook;
import com.getir.readingisgood.model.dto.MonthlyStatisticsDTO;
import com.getir.readingisgood.model.enums.OrderStatus;
import com.getir.readingisgood.service.CustomerOrderService;
import com.getir.readingisgood.service.StatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@DataMongoTest
public class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private CustomerOrderService orderService;

    private CustomerOrder order;

    @Before
    public void init(){
        Integer quantity = Double.valueOf(Math.random()*10).intValue();
        Double price = Double.valueOf(Math.random()*100);

        this.order = new CustomerOrder();

        OrderBook orderBook = new OrderBook();
        orderBook.setQuantity(quantity);

        order.setBookList(Arrays.asList(orderBook));
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPrice(price);
        order.setOrderDate(Instant.now());
    }

    @Test
    public void whenStatisticMonthly_shouldReturnStatisticList(){
        List orderList = Arrays.asList(order);

        when(orderService.findAll()).thenReturn(orderList);

        List<MonthlyStatisticsDTO> statisticsList = statisticsService.getMonthlyStatistics();
        assertThat(statisticsList.get(0).getTotalPurchasedAmount()).isEqualTo(order.getTotalPrice());
        verify(orderService).findAll();
    }

}
