package com.getir.readingisgood.unit;

import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.CustomerOrder;
import com.getir.readingisgood.model.OrderBook;
import com.getir.readingisgood.model.dto.CustomerOrderRequestDTO;
import com.getir.readingisgood.model.dto.CustomerOrderResponseDTO;
import com.getir.readingisgood.model.dto.OrderBookRequestDTO;
import com.getir.readingisgood.model.enums.OrderStatus;
import com.getir.readingisgood.repository.CustomerOrderRepository;
import com.getir.readingisgood.service.BookService;
import com.getir.readingisgood.service.CustomerOrderService;
import com.getir.readingisgood.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@DataMongoTest
public class OrderServiceTest {

    @InjectMocks
    private CustomerOrderService orderService;

    @Mock
    private CustomerOrderRepository orderRepository;

    @Mock
    private BookService bookService;

    @Mock
    private CustomerService customerService;

    private CustomerOrder order;
    private Customer customer;
    private Book book;

    @Before
    public void init(){
        setCustomer();
        setBook();
        setOrder();
    }

    public void setCustomer(){
        customer = new Customer();
        customer.setName("test");
        customer.setSurname("user");
        customer.setEmail("testuser@gmail.com");
    }

    private void setBook(){
        Long stock = Double.valueOf(Math.random()*100).longValue();
        Double price = Double.valueOf(Math.random()*15);

        this.book = new Book();
        this.book.setName("Getir");
        this.book.setPrice(price);
        this.book.setStock(stock);
    }

    public void setOrder(){
        Integer quantity = Double.valueOf(Math.random()*2).intValue();
        Double price = Double.valueOf(Math.random()*100);

        OrderBook orderBook = new OrderBook(book, quantity);

        order = new CustomerOrder();
        order.setBookList(Arrays.asList(orderBook));
        order.setCustomer(customer);
        order.setTotalPrice(price);
        order.setOrderDate(Instant.now());
        order.setStatus(OrderStatus.CREATED);
    }

    private CustomerOrderRequestDTO getOrderRequest(){
        Double price = Double.valueOf(Math.random() * 100);
        String customerId = Double.valueOf(Math.random() * 10).toString();
        String bookId = Double.valueOf(Math.random() * 10).toString();
        Integer quantity = Double.valueOf(Math.random() * 2).intValue();

        OrderBookRequestDTO orderBookDTO = new OrderBookRequestDTO();
        orderBookDTO.setBookId(bookId);
        orderBookDTO.setQuantity(quantity);

        CustomerOrderRequestDTO orderRequestDTO = new CustomerOrderRequestDTO();
        orderRequestDTO.setCustomerId(customerId);
        orderRequestDTO.setTotalPrice(price);
        orderRequestDTO.setBookOrders(Arrays.asList(orderBookDTO));
        return orderRequestDTO;
    }

    @Test
    public void whenSaveOrder_shouldReturnOrderDTO() {
        when(customerService.findCustomerById(ArgumentMatchers.any(String.class))).thenReturn(customer);
        when(bookService.findBookById(ArgumentMatchers.any(String.class))).thenReturn(book);
        when(orderRepository.save(ArgumentMatchers.any(CustomerOrder.class))).thenReturn(order);

        CustomerOrderResponseDTO responseDTO = orderService.createOrder(getOrderRequest());

        assertThat(responseDTO.getCustomerName()).isEqualTo(customer.getName());
        verify(customerService).findCustomerById(ArgumentMatchers.any(String.class));
        verify(bookService).findBookById(ArgumentMatchers.any(String.class));
    }

    @Test
    public void whenGetOrder_shouldReturnOrderDTO() {
        CustomerOrderResponseDTO orderDTO = new CustomerOrderResponseDTO(order);
        when(orderRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(order));

        String id = Double.valueOf(Math.random() * 10).toString();
        CustomerOrderResponseDTO responseOrderDTO = orderService.getOrderById(id);

        assertThat(orderDTO.getCustomerName()).isEqualTo(responseOrderDTO.getCustomerName());
        verify(orderRepository).findById(ArgumentMatchers.any(String.class));
    }

    @Test
    public void whenFindAllOrder_shouldReturnOrderList() {
        List<CustomerOrder> orderList = Arrays.asList(order);
        when(orderRepository.findAll()).thenReturn(orderList);

        List<CustomerOrder> serviceOrderList = orderService.findAll();

        assertThat(serviceOrderList.size()).isEqualTo(orderList.size());
        verify(orderRepository).findAll( );
    }

    @Test
    public void whenFindOrderById_shouldReturnOrder() {
        when(orderRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(order));

        String id = Double.valueOf(Math.random() * 10).toString();
        CustomerOrder newOrder = orderService.findOrderById(id);

        assertThat(order).isSameAs(newOrder);
        verify(orderRepository).findById(ArgumentMatchers.any(String.class));
    }

}
