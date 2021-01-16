package ru.otus.spring.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.entity.Book;
import ru.otus.spring.model.BookDtoIn;
import ru.otus.spring.service.BookService;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/book")
    public String listPage(Model model) {
        List<Book> books = bookService.findAll();

        //данный код написан в целях ознакомления и демонстрации работы Hystrix
        List<Book> hystrixBooks = new ArrayList<>();
        bookService.addedEmptyBooks(hystrixBooks, books.size());
        bookService.copyBookNames(books, hystrixBooks);
        bookService.copyBookAuthors(books, hystrixBooks);
        bookService.copyBookGenres(books, hystrixBooks);

        model.addAttribute("books", hystrixBooks);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("name", userDetails.getUsername());
        model.addAttribute("roles", userDetails.getAuthorities());
        return "list";
    }

    @GetMapping(value = "/book/{id}")
    public String bookByIdPage(Model model,
                               @PathVariable(value = "id") int bookId) {
        Book book = bookService.getById(bookId);
        model.addAttribute("book", book);
        return "edit";
    }

    @GetMapping(value = "/book/delete/{id}")
    public String submitDeletionBookByIdPage(Model model,
                                             @PathVariable(value = "id") int bookId) {
        Book book = bookService.getById(bookId);
        model.addAttribute("book", book);
        return "submit_deletion";
    }

    @PostMapping(value = "/book/edit/{id}")
    public String editBookById(@PathVariable(value = "id") long bookId,
                               @ModelAttribute("book") BookDtoIn bookIn) {
        bookService.updateBook(bookId, bookIn.getName(), bookIn.getAuthor(), bookIn.getGenre());
        return "redirect:/book";
    }

    @GetMapping(value = "/book/new")
    public String addNewBookPage() {
        return "save";
    }

    @PostMapping(value = "/book/create")
    public String addNewBook(@ModelAttribute("book") BookDtoIn bookIn) {
        bookService.addNewBook(bookIn.getName(), bookIn.getAuthor(), bookIn.getGenre());
        return "redirect:/book";
    }

    @PostMapping(value = "/book/delete/{id}")
    public String deleteBookById(@PathVariable(value = "id") int bookId) {
        bookService.deleteById(bookId);
        return "redirect:/book";
    }


}
