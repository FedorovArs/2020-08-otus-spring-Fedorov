package ru.otus.spring.service;

import ru.otus.spring.entity.Book;

import java.util.List;

public interface BookService {

    void deleteById(long id);

    Book getById(long id);

    Book addNewBook(String name, String author, String genre);

    Book updateBook(long bookId, String name, String author, String genre);

    List<Book> findAll();
}
