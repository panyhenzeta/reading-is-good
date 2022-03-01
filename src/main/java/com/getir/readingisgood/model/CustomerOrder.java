package com.getir.readingisgood.model;

import com.getir.readingisgood.model.enums.OrderStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document("CustomerOrder")
@Data
public class CustomerOrder {

    @Id
    private String id;
    private Customer customer;
    private Double totalPrice;
    private Instant orderDate;
    private OrderStatus status;
    private List<OrderBook> bookList;
}
