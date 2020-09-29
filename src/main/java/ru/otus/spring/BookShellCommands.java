package ru.otus.spring;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.BookServiceImpl;

import java.util.stream.Collectors;

@ShellComponent
public class BookShellCommands {

    private final BookServiceImpl bookServiceImpl;

    public BookShellCommands(BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    @ShellMethod(value = "Get all", key = {"all"})
    public String all() {
        return bookServiceImpl.getAll().stream()
                .map(Book::toString)
                .collect(Collectors.joining(",\n"));
    }

    @ShellMethod(value = "Create book", key = {"create"})
    public String create(@ShellOption String name,
                         @ShellOption String author,
                         @ShellOption String genre) {

        return bookServiceImpl.addNewBook(name.trim(), author.trim(), genre.trim());
    }

    @ShellMethod(value = "Update book", key = {"update"})
    public String update(@ShellOption(defaultValue = "1") long id,
                         @ShellOption String name,
                         @ShellOption String author,
                         @ShellOption String genre) {

        return bookServiceImpl.updateBook(id, name.trim(), author.trim(), genre.trim());
    }

    @ShellMethod(value = "Get by id", key = {"get"})
    public String getById(@ShellOption(defaultValue = "1") long id) {
        return bookServiceImpl.getById(id);
    }

    @ShellMethod(value = "Delete by id", key = {"delete"})
    public void deleteById(@ShellOption(defaultValue = "1") long id) {
        bookServiceImpl.deleteById(id);
    }
}
