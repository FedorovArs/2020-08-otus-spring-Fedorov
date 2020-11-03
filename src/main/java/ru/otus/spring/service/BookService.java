package ru.otus.spring.service;

import ru.otus.spring.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookService {

    void deleteById(long id);

    Book getById(long id);

    Book addNewBook(Map<String, String> newBookData);

    Book updateBook(long bookId, Map<String, String> updatedData);

    List<Book> findAll();
}
