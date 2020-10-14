package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.entity.Author;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Comment;
import ru.otus.spring.entity.Genre;
import ru.otus.spring.repository.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CustomAuthorRepository customAuthorRepository;
    private final CustomGenreRepository customGenreRepository;
    private final CustomCommentRepository customCommentRepository;

    public BookServiceImpl(BookRepository bookRepository, CustomAuthorRepositoryImpl customAuthorRepository,
                           CustomGenreRepository customGenreRepository, CustomCommentRepository customCommentRepository) {

        this.bookRepository = bookRepository;
        this.customCommentRepository = customCommentRepository;
        this.customAuthorRepository = customAuthorRepository;
        this.customGenreRepository = customGenreRepository;
    }

    @Transactional(readOnly = true)
    public String getAll() {
        return bookRepository.findAll().stream()
                .map(Book::toString)
                .collect(Collectors.joining(",\n"));
    }

    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public String getById(long id) {
        Optional<Book> byId = bookRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get().toString();
        } else {
            return "Книга с указанным id отсутствует в БД";
        }
    }

    @Transactional
    public String addNewBook(String bookName, String authorName, String genreName) {

        Author author = customAuthorRepository.getByNameOrCreate(new Author(null, authorName));
        Genre genre = customGenreRepository.getByNameOrCreate(new Genre(null, genreName));

        bookRepository.save(new Book(null, bookName, author, genre));
        return "Книга успешно добавлена";
    }

    @Transactional
    public String updateBook(long id, String newBookName, String newAuthor, String newGenre, String newComment) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()) {
            return "Книга с указанным id отсутсвует в БД";
        } else {
            Book book = optionalBook.get();
            if (book.getName().equalsIgnoreCase(newBookName)
                    && book.getAuthor().getName().equalsIgnoreCase(newAuthor)
                    && book.getGenre().getName().equalsIgnoreCase(newGenre)) {
                return "Книги с указанным id уже имеет указанные атрибуты. Изменения не требуются.";
            }

            Author author = customAuthorRepository.getByNameOrCreate(new Author(null, newAuthor));
            Genre genre = customGenreRepository.getByNameOrCreate(new Genre(null, newGenre));
            Comment comment = customCommentRepository.getByTextOrCreate(new Comment(null, book, newComment));

            bookRepository.save(new Book(id, newBookName, author, genre, comment));
            return "Книга успешно обновлена";
        }
    }
}
