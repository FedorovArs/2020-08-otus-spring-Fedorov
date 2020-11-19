package ru.otus.spring.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class BookPageController {

    @GetMapping("/books")
    public String booksListPage(Model model) {
        model.addAttribute("keywords", "list best books, list books, list books free");
        return "list";
    }

    @GetMapping(value = "/books/edit/{id}")
    public String editBookByIdPage(Model model) {
        model.addAttribute("keywords", "edit books");
        return "edit";
    }

    @GetMapping(value = "/books/new")
    public String addNewBookPage() {
        return "save";
    }

    @PostMapping(value = "/books/create")
    public String newBookRedirectPage() {
        return "redirect:/book";
    }

    @GetMapping(value = "/books/delete")
    public String deleteBookByIdPage() {
        return "redirect:/book";
    }
}
