package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.entity.Author;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

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
    public Book getById(long id) {
        return bookRepository.getOne(id);
    }

    @Transactional
    public Book addNewBook(String bookName, String authorName, String genreName) {

        Author author = authorRepository.getByNameOrCreate(new Author(null, authorName));
        Genre genre = genreRepository.getByNameOrCreate(new Genre(null, genreName));

        return bookRepository.save(new Book(null, bookName, author, genre));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(long bookId, String name, String author, String genre) {
        Book book = bookRepository.getOne(bookId);

        book.setName(name);
        book.getAuthor().setName(author);
        book.getGenre().setName(genre);

        return bookRepository.save(book);
    }
}
