package com.getir.readingisgood.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Customer")
@Data
public class Customer {

    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
}
