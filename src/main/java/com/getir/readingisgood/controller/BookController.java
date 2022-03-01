package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.model.response.SuccessResponse;
import com.getir.readingisgood.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Book controller for book and book stock operations.
 */
@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    /**
     * Endpoint that create book.
     *
     * @param book DTO object for book.
     * @return success response with saved object.
     */
    @PostMapping("/books")
    public ResponseEntity<SuccessResponse<Book>> createBook(@Valid @RequestBody BookDTO book) {
        Book savedBook = bookService.createBook(book);
        return ResponseEntity.ok().body(new SuccessResponse<>(savedBook));
    }

    /**
     * Endpoint that update stock of book.
     *
     * @param bookId for existing book.
     * @param stock for increasing or decreasing.
     * @return success response with saved object.
     */
    @PutMapping("/book/update-stock")
    public ResponseEntity<SuccessResponse<Book>> updateStock(@Valid @RequestParam String bookId, @Valid @RequestParam Long stock){
        Book updatedBook = bookService.updateStock(bookId, stock);
        return ResponseEntity.ok().body(new SuccessResponse<>(updatedBook));
    }

}
