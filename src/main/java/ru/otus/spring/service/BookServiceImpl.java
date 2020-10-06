package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.AuthorRepositoryJpa;
import ru.otus.spring.dao.BookRepositoryJpa;
import ru.otus.spring.dao.CommentRepositoryJpa;
import ru.otus.spring.dao.GenreRepositoryJpa;
import ru.otus.spring.entity.Author;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Comment;
import ru.otus.spring.entity.Genre;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryJpa bookRepositoryJpa;
    private final AuthorRepositoryJpa authorRepositoryJpa;
    private final GenreRepositoryJpa genreRepositoryJpa;
    private final CommentRepositoryJpa commentRepositoryJpa;

    public BookServiceImpl(BookRepositoryJpa bookRepositoryJpa, AuthorRepositoryJpa authorRepositoryJpa,
                           GenreRepositoryJpa genreRepositoryJpa, CommentRepositoryJpa commentRepositoryJpa) {

        this.bookRepositoryJpa = bookRepositoryJpa;
        this.authorRepositoryJpa = authorRepositoryJpa;
        this.genreRepositoryJpa = genreRepositoryJpa;
        this.commentRepositoryJpa = commentRepositoryJpa;
    }

    @Transactional(readOnly = true)
    public String getAll() {
        return bookRepositoryJpa.findAll().stream()
                .map(Book::toString)
                .collect(Collectors.joining(",\n"));
    }

    @Transactional
    public void deleteById(long id) {
        bookRepositoryJpa.deleteById(id);
    }

    @Transactional(readOnly = true)
    public String getById(long id) {
        Optional<Book> byId = bookRepositoryJpa.findById(id);
        if (byId.isPresent()) {
            return byId.get().toString();
        } else {
            return "Книга с указанным id отсутствует в БД";
        }
    }

    @Transactional
    public String addNewBook(String bookName, String authorName, String genreName) {

        Author author = authorRepositoryJpa.getByNameOrCreate(new Author(null, authorName));
        Genre genre = genreRepositoryJpa.getByNameOrCreate(new Genre(null, genreName));

        bookRepositoryJpa.save(new Book(null, bookName, author, genre));
        return "Книга успешно добавлена";
    }

    @Transactional
    public String updateBook(long id, String newBookName, String newAuthor, String newGenre, String newComment) {
        Optional<Book> optionalBook = bookRepositoryJpa.findById(id);

        if (optionalBook.isEmpty()) {
            return "Книга с указанным id отсутсвует в БД";
        } else {
            Book book = optionalBook.get();
            if (book.getName().equalsIgnoreCase(newBookName)
                    && book.getAuthor().getName().equalsIgnoreCase(newAuthor)
                    && book.getGenre().getName().equalsIgnoreCase(newGenre)) {
                return "Книги с указанным id уже имеет указанные атрибуты. Изменения не требуются.";
            }

            Author author = authorRepositoryJpa.getByNameOrCreate(new Author(null, newAuthor));
            Genre genre = genreRepositoryJpa.getByNameOrCreate(new Genre(null, newGenre));
            Comment comment = commentRepositoryJpa.getByTextOrCreate(new Comment(null, book, newComment));

            bookRepositoryJpa.updateById(new Book(id, newBookName, author, genre, comment));
            return "Книга успешно обновлена";
        }
    }
}
