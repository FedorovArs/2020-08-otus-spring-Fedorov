package ru.otus.spring.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.entity.Book;
import ru.otus.spring.service.BookService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public String listPage(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping(value = "/{id}")
    public String bookById(Model model,
                           @PathVariable(value = "id") int bookId) {
        Book book = bookService.getById(bookId);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editBookById(Model model,
                               @PathVariable(value = "id") long bookId,
                               @RequestBody MultiValueMap<String, String> updatedData) {

        String name = updatedData.get("name").get(0);
        String author = updatedData.get("author").get(0);
        String genre = updatedData.get("genre").get(0);

        bookService.updateBook(bookId, name, author, genre);
        model.addAttribute("books", bookService.findAll());
        return "redirect:/book";
    }

    @GetMapping(value = "/new")
    public String addNewBook() {
        return "save";
    }

    @PostMapping(value = "/create")
    public String addNewBook(Model model,
                             @RequestBody MultiValueMap<String, String> updatedData) {

        String name = updatedData.get("name").get(0);
        String author = updatedData.get("author").get(0);
        String genre = updatedData.get("genre").get(0);

        bookService.addNewBook(name, author, genre);
        model.addAttribute("books", bookService.findAll());
        return "redirect:/book";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteBookById(Model model,
                                 @PathVariable(value = "id") int bookId) {
        bookService.deleteById(bookId);
        model.addAttribute("books", bookService.findAll());
        return "redirect:/book";
    }
}
