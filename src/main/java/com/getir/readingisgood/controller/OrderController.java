package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.dto.CustomerOrderRequestDTO;
import com.getir.readingisgood.model.dto.CustomerOrderResponseDTO;
import com.getir.readingisgood.model.response.SuccessResponse;
import com.getir.readingisgood.service.CustomerOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

/**
 * Order controller for create, get and list order operations.
 */
@RestController
@RequestMapping("/api")
public class OrderController {

    private final CustomerOrderService orderService;

    public OrderController(CustomerOrderService orderService){
        this.orderService = orderService;
    }

    /**
     * Endpoint that create order.
     *
     * @param order DTO object for order.
     * @return success response with saved object.
     */
    @PostMapping("/orders")
    public ResponseEntity<SuccessResponse<CustomerOrderResponseDTO>> createOrder(@Valid @RequestBody CustomerOrderRequestDTO order) {
        CustomerOrderResponseDTO response = orderService.createOrder(order);
        return ResponseEntity.ok().body(new SuccessResponse<>(response));
    }

   /**
     * Endpoint that create order.
     *
     * @param id of existing order.
     * @return existing order.
    */
    @GetMapping("/orders/{id}")
    public ResponseEntity<SuccessResponse<CustomerOrderResponseDTO>> getOrder(@PathVariable String id) {
        CustomerOrderResponseDTO response = orderService.getOrderById(id);
        return ResponseEntity.ok().body(new SuccessResponse<>(response));
    }

    /**
     * Endpoint that create order.
     *
     * @param startDate for order date.
     * @param endDate for order date.
     * @return existing list of orders.
     */
    @GetMapping("/orders")
    public ResponseEntity<SuccessResponse<List<CustomerOrderResponseDTO>>> listOrdersByStartDateAndEndDate(@RequestParam Instant startDate, @RequestParam Instant endDate) {
        List<CustomerOrderResponseDTO> response = orderService.listOrdersByStartDateAndEndDate(startDate, endDate);
        return ResponseEntity.ok().body(new SuccessResponse<>(response));
    }
}
