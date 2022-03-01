package com.getir.readingisgood.model.dto;

import com.getir.readingisgood.model.dto.abstractdto.AbstractCustomerOrder;
import com.getir.readingisgood.validator.annotation.BookStockInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CustomerOrderRequestDTO extends AbstractCustomerOrder {

    @NotNull
    private String customerId;

    @BookStockInfo
    private List<OrderBookRequestDTO> bookOrders;
}
