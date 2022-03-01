package com.getir.readingisgood.service;

import com.getir.readingisgood.exception.CustomValidationException;
import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.dto.CustomerDTO;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.util.EmailUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Customer service that create and list operations.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    /**
     * method that create customer.
     *
     * @param customer DTO object for customer.
     * @return saved object.
     */
    public Customer createCustomer(CustomerDTO customer) {
        validation(customer);
        Customer newCustomer = customer.convertCustomer();
        return customerRepository.save(newCustomer);
    }

    /**
     * method that list customers.
     *
     * @param pageable for pagination.
     * @return list of existing customers.
     */
    public List<Customer> findAll(Pageable pageable){
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return customerPage.getContent();
    }

    /**
     * method that find customer by id
     *
     * @param customerId id of existing customer
     * @return existing customer.
     */
    public Customer findCustomerById(String customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new CustomValidationException("error.customerNotFound");
        }
    }


    private void validation(CustomerDTO customer) {
        if (!EmailUtil.isValidMail(customer.getEmail())){
            throw new CustomValidationException("error.emailIsNotValid");
        }
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(customer.getEmail());
        if (optionalCustomer.isPresent()){
            throw new CustomValidationException("error.customerAlreadyExist");
        }
    }
}
