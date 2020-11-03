package ru.otus.spring.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/book")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping(value = "/{id}")
    public BookDto getBookById(@PathVariable(value = "id") int bookId) {
        return bookService.getById(bookId);
    }

    @PutMapping(value = "/edit/{id}")
    public String editBookById(@PathVariable(value = "id") long bookId,
                               @RequestBody Map<String, String> updatedData) {
        bookService.updateBook(bookId, updatedData);
        return "redirect:/book";
    }

    @PostMapping(value = "/api/book/create")
    public boolean addNewBook(@RequestParam Map<String, String> updatedData) {
        bookService.addNewBook(updatedData);
        return true;
    }

    @DeleteMapping(value = "/api/book/{id}")
    public Boolean deleteBookById(@PathVariable(value = "id") int bookId) {
        bookService.deleteById(bookId);
        return true;
    }
}
