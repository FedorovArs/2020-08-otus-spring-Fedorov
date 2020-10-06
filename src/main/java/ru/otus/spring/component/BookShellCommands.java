package ru.otus.spring.component;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.BookServiceImpl;

@ShellComponent
public class BookShellCommands {

    private final BookServiceImpl bookServiceImpl;

    public BookShellCommands(BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    @ShellMethod(value = "Get all", key = {"all"})
    public String all() {
        return bookServiceImpl.getAll();
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
                         @ShellOption String genre,
                         @ShellOption String comment) {

        return bookServiceImpl.updateBook(id, name.trim(), author.trim(), genre.trim(), comment.trim());
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
