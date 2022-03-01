package com.getir.readingisgood.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Book")
@Data
public class Book {

    @Id
    private String id;
    private String name;
    private Double price;
    private Long stock;
}
