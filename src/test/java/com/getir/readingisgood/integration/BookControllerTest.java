package com.getir.readingisgood.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureDataMongo
public class BookControllerTest {

    @Autowired
    private MockMvc mock;

    @InjectMocks
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    private Book book;

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        setBook();
    }

    private void setBook() {
        Double price = Double.valueOf(Math.random() * 10);
        Long stock = Double.valueOf(Math.random() * 10).longValue();

        this.book = new Book();
        this.book.setName("Getir");
        this.book.setPrice(price);
        this.book.setStock(stock);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void whenSaveBook_shouldReturnSuccess() throws Exception {
        this.mock.perform(post("/api/books")
                        .content(asJsonString(new BookDTO(book)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void whenUpdateBookStock_shouldReturnSuccess() throws Exception {
        given(bookRepository.findById(ArgumentMatchers.any(String.class))).willReturn(Optional.ofNullable(book));
        given(bookRepository.save(ArgumentMatchers.any(Book.class))).willReturn(book);

        Long stock = Double.valueOf(Math.random() * 5).longValue();
        Long id = Double.valueOf(Math.random() * 10).longValue();
        this.mock.perform(put("/api/book/update-stock")
                        .param("bookId", id.toString())
                        .param("stock", stock.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
