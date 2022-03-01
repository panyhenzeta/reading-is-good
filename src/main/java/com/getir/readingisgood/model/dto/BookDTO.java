package com.getir.readingisgood.model.dto;

import com.getir.readingisgood.model.Book;

import javax.validation.constraints.NotNull;

public class BookDTO {

    @NotNull
    private String name;

    @NotNull
    private Double price;

    public BookDTO() {
    }

    public BookDTO(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public BookDTO(Book book) {
        this.name = book.getName();
        this.price = book.getPrice();
    }

    public Book convertBook(){
        Book book = new Book();
        book.setName(this.name);
        book.setPrice(this.price);
        return book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
