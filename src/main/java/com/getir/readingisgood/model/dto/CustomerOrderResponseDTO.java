package com.getir.readingisgood.model.dto;

import com.getir.readingisgood.model.CustomerOrder;
import com.getir.readingisgood.model.dto.abstractdto.AbstractCustomerOrder;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerOrderResponseDTO extends AbstractCustomerOrder {
    private String customerName;
    private String customerSurname;
    private String orderStatus;
    private List<OrderBookResponseDTO> bookList;

    public CustomerOrderResponseDTO(CustomerOrder order){
        this.customerName = order.getCustomer().getName();
        this.customerSurname = order.getCustomer().getSurname();
        this.totalPrice = order.getTotalPrice();
        this.orderStatus = order.getStatus().name();
        this.bookList = order.getBookList()
                .stream()
                .map(orderBook -> {
                    return new OrderBookResponseDTO(orderBook);
                }).collect(Collectors.toList());
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderBookResponseDTO> getBookList() {
        return bookList;
    }

    public void setBookList(List<OrderBookResponseDTO> bookList) {
        this.bookList = bookList;
    }
}
