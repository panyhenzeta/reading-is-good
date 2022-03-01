package com.getir.readingisgood.service;

import com.getir.readingisgood.exception.CustomValidationException;
import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Book service that create and update operations.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    /**
     * method that give monthly statistics.
     *
     * @param book DTO object for book.
     * @return saved object.
     */
    public Book createBook(BookDTO book){
        Book newBook = book.convertBook();
        newBook.setStock(1L);
        return bookRepository.save(newBook);
    }

    /**
     * method that update stock of book.
     *
     * @param bookId for existing book.
     * @param stock for increasing or decreasing.
     * @return updated object.
     */
    @Transactional
    public Book updateStock(String bookId, Long stock){
        AtomicReference<Book> updatedBook = new AtomicReference<>(new Book());
        bookRepository.findById(bookId)
                .ifPresentOrElse(book -> {
                    Long stockOfBook = book.getStock();
                    book.setStock(stockOfBook + stock);
                    updatedBook.set(bookRepository.save(book));
                }, () -> {
                    throw new CustomValidationException("error.notFoundBook");
                });
        return updatedBook.get();
    }

    @Transactional
    public void updateStock(Book book){
        bookRepository.save(book);
    }

    public Book findBookById(String bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new CustomValidationException("error.bookNotFound");
        }
    }
}
