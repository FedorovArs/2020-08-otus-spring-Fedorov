package ru.otus.spring.service;

public interface BookService {

    String getAll();

    void deleteById(long id);

    String getById(long id);

    String addNewBook(String bookName, String authorName, String genreName, String comment);

    String updateBook(long id, String newBookName, String newAuthor, String newGenre, String newComment);

}
