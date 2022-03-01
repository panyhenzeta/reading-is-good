package com.getir.readingisgood.service;


import com.getir.readingisgood.exception.CustomValidationException;
import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.CustomerOrder;
import com.getir.readingisgood.model.OrderBook;
import com.getir.readingisgood.model.dto.CustomerOrderRequestDTO;
import com.getir.readingisgood.model.dto.CustomerOrderResponseDTO;
import com.getir.readingisgood.model.dto.OrderBookRequestDTO;
import com.getir.readingisgood.model.enums.OrderStatus;
import com.getir.readingisgood.repository.CustomerOrderRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Order service that create, get and list operations.
 */
@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final BookService bookService;
    private final CustomerService customerService;
    private final ReentrantLock lock = new ReentrantLock();


    public CustomerOrderService(CustomerOrderRepository customerOrderRepository, BookService bookService, CustomerService customerService){
        this.customerOrderRepository = customerOrderRepository;
        this.bookService = bookService;
        this.customerService = customerService;
    }

    /**
     * method that create order.
     *
     * @param order DTO object for order.
     * @return saved object.
     */
    public CustomerOrderResponseDTO createOrder(CustomerOrderRequestDTO order) {
        CustomerOrder newOrder = new CustomerOrder();
        newOrder.setTotalPrice(order.getTotalPrice());
        newOrder.setOrderDate(Instant.now());
        newOrder.setStatus(OrderStatus.CREATED);

        Customer customer = customerService.findCustomerById(order.getCustomerId());
        newOrder.setCustomer(customer);

        List<OrderBook> orderBookList = getOrderBooks(order.getBookOrders());
        newOrder.setBookList(orderBookList);
        CustomerOrder customerOrder = customerOrderRepository.save(newOrder);
        CustomerOrderResponseDTO orderResponseDTO = new CustomerOrderResponseDTO(customerOrder);
        return orderResponseDTO;
    }

    /**
     * Method that create order.
     *
     * @param orderId of existing order.
     * @return existing order.
     */
    public CustomerOrderResponseDTO getOrderById(String orderId) {
        CustomerOrder order = findOrderById(orderId);
        CustomerOrderResponseDTO orderDTO = new CustomerOrderResponseDTO(order);
        return orderDTO;
    }

    /**
     * Method that create order.
     *
     * @param startDate for order date.
     * @param endDate for order date.
     * @return existing list of orders.
     */
    public List<CustomerOrderResponseDTO> listOrdersByStartDateAndEndDate(Instant startDate, Instant endDate) {
        List<CustomerOrderResponseDTO> customerOrderList = customerOrderRepository.findByOrderDateBetween(startDate, endDate)
                        .stream().map(customerOrder -> {
                            return new CustomerOrderResponseDTO(customerOrder);
                        }).collect(Collectors.toList());
        return customerOrderList;
    }

    public CustomerOrder findOrderById(String orderId){
        Optional<CustomerOrder> order = customerOrderRepository.findById(orderId);
        if (order.isPresent()){
            return order.get();
        } else {
            throw new CustomValidationException("error.orderNotFound");
        }
    }

    public List<CustomerOrder> findAll(){
        return customerOrderRepository.findAll();
    }

    private List<OrderBook> getOrderBooks(List<OrderBookRequestDTO> orderBooks) {
        List<OrderBook> orderBookList = orderBooks
                .stream()
                .map(orderBook -> {
                    Book book = bookService.findBookById(orderBook.getBookId());
                    checkAndUpdateBookStock(book, orderBook.getQuantity());
                    return new OrderBook(book, orderBook.getQuantity());
                }).collect(Collectors.toList());
        return orderBookList;
    }

    private void checkAndUpdateBookStock(Book book, int quantity) {
        lock.lock();
        if (book.getStock().intValue() < quantity){
            throw new CustomValidationException("error.bookQuantityIsNotEnough." + book.getName());
        }
        Long newStock = book.getStock() - quantity;
        book.setStock(newStock);
        bookService.updateStock(book);
        lock.unlock();
    }
}
