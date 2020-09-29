package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    int count();

    void insert(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);

    void updateById(Book book);
}
