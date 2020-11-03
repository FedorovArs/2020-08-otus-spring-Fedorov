package ru.otus.spring.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.entity.Book;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping
public class BookController {

    private final BookService bookService;

    @GetMapping("/book")
    public String listPage(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping(value = "/book/{id}")
    public String bookByIdPage(Model model,
                               @PathVariable(value = "id") int bookId) {
        Book book = bookService.getById(bookId);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping(value = "/book/edit/{id}")
    public String editBookById(Model model,
                               @PathVariable(value = "id") long bookId,
                               @RequestBody Map<String, String> updatedData) {

        bookService.updateBook(bookId, updatedData);
        model.addAttribute("books", bookService.findAll());
        return "redirect:/book";
    }

    @GetMapping(value = "/book/new")
    public String addNewBookPage() {
        return "save";
    }

    @PostMapping(value = "/book/create")
    public String addNewBook(Model model,
                             @RequestBody Map<String, String> newBookData) {
        bookService.addNewBook(newBookData);
        model.addAttribute("books", bookService.findAll());
        return "redirect:/book";
    }

    @GetMapping(value = "/book/delete/{id}")
    public String deleteBookById(Model model,
                                 @PathVariable(value = "id") int bookId) {
        bookService.deleteById(bookId);
        model.addAttribute("books", bookService.findAll());
        return "redirect:/book";
    }
}
