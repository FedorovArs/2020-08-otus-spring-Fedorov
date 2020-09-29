package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    void deleteById(long id);

    String getById(long id);

    String addNewBook(String bookName, String authorName, String genreName);

    String updateBook(long id, String newBookName, String newAuthor, String newGenre);

}
