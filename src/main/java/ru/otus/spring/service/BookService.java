package ru.otus.spring.service;

public interface BookService {

    String getAll();

    void deleteById(String id);

    String getById(String id);

    String addNewBook(String bookName, String authorName, String genreName);

    String updateBook(String id, String newBookName, String newAuthor, String newGenre, String newComment);

}
