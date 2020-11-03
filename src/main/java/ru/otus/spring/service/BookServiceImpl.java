package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.entity.Author;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BookDto getById(long id) {
        return bookRepository.getOne(id).toDto();
    }

    @Transactional
    public BookDto addNewBook(Map<String, String> updatedData) {
        String name = updatedData.get("name");
        String author = updatedData.get("author");
        String genre = updatedData.get("genre");

        Author authorEntity = authorRepository.getByNameOrCreate(new Author(null, author));
        Genre genreEntity = genreRepository.getByNameOrCreate(new Genre(null, genre));

        return bookRepository.save(new Book(null, name, authorEntity, genreEntity)).toDto();
    }

    @Override
    @Transactional
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(Book::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto updateBook(long bookId, Map<String, String> updatedData) {

        String name = updatedData.get("name");
        String author = updatedData.get("author");
        String genre = updatedData.get("genre");

        Book book = bookRepository.getOne(bookId);

        book.setName(name);
        book.getAuthor().setName(author);
        book.getGenre().setName(genre);

        return bookRepository.save(book).toDto();
    }
}
