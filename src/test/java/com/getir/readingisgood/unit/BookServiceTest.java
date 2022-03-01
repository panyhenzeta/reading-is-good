package com.getir.readingisgood.unit;

import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@DataMongoTest
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    private Book book;

    @Before
    public void init(){
        Long stock = Double.valueOf(Math.random() * 5).longValue();
        Double price = Double.valueOf(Math.random() * 5);

        this.book = new Book();
        this.book.setName("Getir");
        this.book.setPrice(price);
        this.book.setStock(stock);
    }


    @Test
    public void whenSaveBook_shouldReturnBook() {
        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(book);
        Book newBook = bookService.createBook(new BookDTO(book));

        assertThat(book.getName()).isSameAs(newBook.getName());
        verify(bookRepository).save(ArgumentMatchers.any(Book.class));
    }

    @Test
    public void whenFindBookById_shouldReturnBook() {
        when(bookRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(book));

        String id = Double.valueOf(Math.random() * 5).toString();
        Book foundBook = bookService.findBookById(id);

        assertThat(foundBook.getName()).isSameAs(book.getName());
        verify(bookRepository).findById(ArgumentMatchers.any(String.class));
    }

    @Test
    public void whenUpdateStock_shouldReturnNewUpdatedStockBook() {
        when(bookRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(book));
        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(book);


        Long stock = Double.valueOf(Math.random() * 10).longValue();
        String id = Double.valueOf(Math.random() * 10).toString();
        Book updatedBook = bookService.updateStock(id, stock) ;

        assertThat(book.getStock()).isEqualTo(updatedBook.getStock());
        verify(bookRepository).findById(ArgumentMatchers.any(String.class));
    }

}
