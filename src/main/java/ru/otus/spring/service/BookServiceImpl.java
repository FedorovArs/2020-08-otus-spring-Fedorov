package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.entity.Author;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Comment;
import ru.otus.spring.entity.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;
    private final PresentationService presentationService;

    public BookServiceImpl(BookRepository bookRepository,
                           GenreRepository genreRepository, CommentRepository commentRepository,
                           PresentationService presentationService, AuthorRepository authorRepository) {

        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.presentationService = presentationService;
    }

    @Transactional(readOnly = true)
    public String getAll() {
        List<Book> books = bookRepository.findAll();
        return presentationService.convertBooksForShellPresentation(books);
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

        Author author = authorRepository.getByNameOrCreate(new Author(null, authorName));
        Genre genre = genreRepository.getByNameOrCreate(new Genre(null, genreName));

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

            Author author = authorRepository.getByNameOrCreate(new Author(null, newAuthor));
            Genre genre = genreRepository.getByNameOrCreate(new Genre(null, newGenre));
            Comment comment = commentRepository.getByTextOrCreate(new Comment(null, book, newComment));

            bookRepository.save(new Book(id, newBookName, author, genre, comment));
            return "Книга успешно обновлена";
        }
    }
}
