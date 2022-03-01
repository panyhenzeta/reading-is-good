package com.getir.readingisgood.model.dto;

import com.getir.readingisgood.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @NotNull
    private String email;
    private String name;
    private String surname;

    public CustomerDTO(Customer customer){
        this.email = customer.getEmail();
        this.name = customer.getName();
        this.surname = customer.getSurname();
    }

    public Customer convertCustomer(){
        Customer customer = new Customer();
        customer.setEmail(this.email);
        customer.setName(this.name);
        customer.setSurname(this.surname);
        return customer;
    }
}
