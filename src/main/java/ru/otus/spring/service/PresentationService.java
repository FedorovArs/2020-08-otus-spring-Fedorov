package ru.otus.spring.service;

import ru.otus.spring.entity.Book;

import java.util.List;

public interface PresentationService {
    String convertBooksForShellPresentation(List<Book> books);
}
