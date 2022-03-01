package com.getir.readingisgood.unit;

import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.dto.CustomerDTO;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@DataMongoTest
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    private CustomerDTO customerDTO;


    @Before
    public void init(){
        customerDTO = new CustomerDTO("testuser@gmail.com", "test", "user");
    }

    @Test
    public void whenSaveCustomer_shouldReturnCustomer() {
        when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(customerDTO.convertCustomer());

        Customer created = customerService.createCustomer(customerDTO);

        assertThat(created.getEmail()).isSameAs(customerDTO.getEmail());
        verify(customerRepository).save(ArgumentMatchers.any(Customer.class));
    }

    @Test
    public void whenFindAllCustomer_shouldReturnCustomerList() {
        List customerList = Arrays.asList(customerDTO.convertCustomer());
        Page page = new PageImpl(customerList);
        Pageable pageable = PageRequest.of(0, 1);

        when(customerRepository.findAll(pageable)).thenReturn(page);
        List<Customer> list = customerService.findAll(pageable);

        assertThat(list.size()).isSameAs(customerList.size());
        verify(customerRepository).findAll(pageable);
    }

    @Test
    public void whenFindCustomerById_shouldReturnCustomer() {
        when(customerRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(customerDTO.convertCustomer()));

        String id = Double.valueOf(Math.random() * 5).toString();
        Customer foundCustomer = customerService.findCustomerById(id);

        assertThat(foundCustomer.getEmail()).isSameAs(customerDTO.getEmail());
        verify(customerRepository).findById(ArgumentMatchers.any(String.class));
    }
}
