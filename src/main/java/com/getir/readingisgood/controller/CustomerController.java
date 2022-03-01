package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.dto.CustomerDTO;
import com.getir.readingisgood.model.response.SuccessResponse;
import com.getir.readingisgood.service.CustomerService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Customer controller for create and list customer operations.
 */
@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    /**
     * Endpoint that create customer.
     *
     * @param customer DTO object for customer.
     * @return success response with saved object.
     */
    @PostMapping("/customers")
    public ResponseEntity<SuccessResponse<Customer>> createCustomer(@Valid @RequestBody CustomerDTO customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return  ResponseEntity.ok().body(new SuccessResponse<>(savedCustomer));
    }

    /**
     * Endpoint that list customers.
     *
     * @param pageable for pagination.
     * @return list of existing customers.
     */
    @GetMapping("/customers")
    public ResponseEntity<SuccessResponse<List<Customer>>> listCustomers(Pageable pageable) {
        List<Customer> customerResponse = customerService.findAll(pageable);
        return ResponseEntity.ok().body(new SuccessResponse<>(customerResponse));
    }
}
