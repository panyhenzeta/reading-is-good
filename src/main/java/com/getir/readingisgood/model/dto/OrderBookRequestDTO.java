package com.getir.readingisgood.model.dto;

import com.getir.readingisgood.model.dto.abstractdto.AbstractOrderBook;
import lombok.Data;

@Data
public class OrderBookRequestDTO extends AbstractOrderBook {
    private String bookId;
}
