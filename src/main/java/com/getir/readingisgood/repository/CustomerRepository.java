package com.getir.readingisgood.repository;

import com.getir.readingisgood.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>, PagingAndSortingRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);
}
