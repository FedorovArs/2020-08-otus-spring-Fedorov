package ru.otus.spring.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BookPageController {

    @GetMapping()
    public String booksListPage(Model model) {
        model.addAttribute("keywords", "list best books, list books, list books free");
        return "list";
    }

    //    Поидее тут д.б. @PutMapping, но я не знаю как из формы отправить put запрос
    @GetMapping(value = "/edit/**")
    public String editBookByIdPage(Model model) {
        model.addAttribute("keywords", "edit books");
        return "edit";
    }

    @GetMapping(value = "/new")
    public String addNewBookPage() {
        return "save";
    }

    @PostMapping(value = "/create")
    public String newBookRedirectPage() {
        return "redirect:/book";
    }

    //    Поидее тут д.б. @DeleteMapping, но я не знаю как из формы отправить delete запрос
    @GetMapping(value = "/delete")
    public String deleteBookByIdPage() {
        return "redirect:/book";
    }
}
