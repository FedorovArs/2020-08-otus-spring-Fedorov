package ru.otus.spring.dao;

import ru.otus.spring.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    long count();

    Book save(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);

    void updateById(Book book);
}
