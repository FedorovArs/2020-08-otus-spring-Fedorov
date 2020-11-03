package ru.otus.spring.service;

import ru.otus.spring.dto.BookDto;

import java.util.List;
import java.util.Map;

public interface BookService {

    void deleteById(long id);

    BookDto getById(long id);

    BookDto addNewBook(Map<String, String> updatedData);

    BookDto updateBook(long bookId, Map<String, String> updatedData);

    List<BookDto> findAll();
}
