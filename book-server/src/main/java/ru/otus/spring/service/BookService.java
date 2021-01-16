package ru.otus.spring.service;

import ru.otus.spring.entity.Book;

import java.util.List;

public interface BookService {

    void deleteById(long id);

    Book getById(long id);

    Book addNewBook(String name, String author, String genre);

    Book updateBook(long bookId, String name, String author, String genre);

    List<Book> findAll();

    void addedEmptyBooks(List<Book> hystrixBooks, int size);

    void copyBookNames(List<Book> books, List<Book> hystrixBooks);

    void copyBookAuthors(List<Book> books, List<Book> hystrixBooks);

    void copyBookGenres(List<Book> books, List<Book> hystrixBooks);
}
