package com.getir.readingisgood.repository;

import com.getir.readingisgood.model.CustomerOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface CustomerOrderRepository extends MongoRepository<CustomerOrder, String>{

    List<CustomerOrder> findByOrderDateBetween(Instant startDate, Instant endDate);
}
