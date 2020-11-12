package ru.otus.spring.service;

import ru.otus.spring.dto.BookDto;

import java.util.List;

public interface BookService {

    void deleteById(long id);

    BookDto getById(long id);

    BookDto addNewBook(BookDto bookDto);

    BookDto updateBook(long bookId, BookDto bookDto);

    List<BookDto> findAll();
}
