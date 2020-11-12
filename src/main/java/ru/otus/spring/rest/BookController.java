package ru.otus.spring.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.service.BookService;

import java.util.List;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping(value = "/books/{id}")
    public BookDto getBookById(@PathVariable(value = "id") int bookId) {
        return bookService.getById(bookId);
    }

    @PutMapping(value = "/books/{id}")
    public void editBookById(@PathVariable(value = "id") long bookId,
                             @RequestBody BookDto bookDto) {
        bookService.updateBook(bookId, bookDto);
    }

    @PostMapping(value = "/books")
    public void addNewBook(@RequestBody BookDto bookDto) {
        bookService.addNewBook(bookDto);
    }

    @DeleteMapping(value = "/books/{id}")
    public void deleteBookById(@PathVariable(value = "id") int bookId) {
        bookService.deleteById(bookId);
    }
}
