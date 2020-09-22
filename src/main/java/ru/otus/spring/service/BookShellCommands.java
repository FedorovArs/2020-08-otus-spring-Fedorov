package ru.otus.spring.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.BookFullView;

import java.util.stream.Collectors;

@ShellComponent
public class BookShellCommands {

    private final FullBookService fullBookService;

    public BookShellCommands(FullBookService fullBookService) {
        this.fullBookService = fullBookService;
    }

    @ShellMethod(value = "Get all", key = {"all"})
    public String all() {
        return fullBookService.getAllView().stream()
                .map(BookFullView::toString)
                .collect(Collectors.joining(",\n"));
    }

    @ShellMethod(value = "Create book", key = {"create"})
    public String create(@ShellOption String name,
                         @ShellOption String author,
                         @ShellOption String genre) {

        return fullBookService.addNewBook(name.trim(), author.trim(), genre.trim());
    }

    @ShellMethod(value = "Update book", key = {"update"})
    public String update(@ShellOption(defaultValue = "1") int id,
                         @ShellOption String name,
                         @ShellOption String author,
                         @ShellOption String genre) {

        return fullBookService.updateBook(id, name.trim(), author.trim(), genre.trim());
    }

    @ShellMethod(value = "Get by id", key = {"get"})
    public String getById(@ShellOption(defaultValue = "1") int id) {
        return fullBookService.getById(id);
    }

    @ShellMethod(value = "Delete by id", key = {"delete"})
    public void deleteById(@ShellOption(defaultValue = "1") int id) {
        fullBookService.deleteById(id);
    }
}
