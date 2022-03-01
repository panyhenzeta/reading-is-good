package com.getir.readingisgood.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.model.CustomerOrder;
import com.getir.readingisgood.model.OrderBook;
import com.getir.readingisgood.model.enums.OrderStatus;
import com.getir.readingisgood.service.CustomerOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureDataMongo
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private CustomerOrderService orderService;

    private CustomerOrder order;
    private Integer quantity;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        setOrder();
    }

    private void setOrder() {
        order = new CustomerOrder();

        quantity = Double.valueOf(Math.random() * 5).intValue();
        Double price = Double.valueOf(Math.random() * 10);

        OrderBook orderBook = new OrderBook();
        orderBook.setQuantity(quantity);

        order.setBookList(Arrays.asList(orderBook));
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPrice(price);
        order.setOrderDate(Instant.now());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void whenMonthlyStatistic_shouldReturnMonthlyStatistic() throws Exception {
        given(orderService.findAll()).willReturn(Arrays.asList(order));
        this.mock.perform(get("/api/statistics/monthly"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].totalBookCount", is(quantity)));
    }

}
