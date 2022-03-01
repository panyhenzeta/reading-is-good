package com.getir.readingisgood.validator;

import com.getir.readingisgood.model.dto.OrderBookRequestDTO;
import com.getir.readingisgood.validator.annotation.BookStockInfo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class BookStockInfoValidator implements ConstraintValidator<BookStockInfo, List<OrderBookRequestDTO>> {

    @Override
    public void initialize(BookStockInfo constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<OrderBookRequestDTO> orderBookRequestDTOS, ConstraintValidatorContext constraintValidatorContext) {
        return !orderBookRequestDTOS
                .stream()
                .anyMatch(orderBookRequestDTO -> orderBookRequestDTO.getQuantity() < 0);

    }
}
