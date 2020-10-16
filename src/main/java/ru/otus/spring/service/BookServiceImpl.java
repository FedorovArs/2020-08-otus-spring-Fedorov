package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Comment;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final PresentationService presentationService;

    public BookServiceImpl(BookRepository bookRepository, CommentRepository commentRepository,
                           PresentationService presentationService) {

        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.presentationService = presentationService;
    }

    @Transactional(readOnly = true)
    public String getAll() {
        List<Book> books = bookRepository.findAll();
        return presentationService.convertBooksForShellPresentation(books);
    }

    @Transactional
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public String getById(String id) {
        Optional<Book> byId = bookRepository.findById(id);
        if (byId.isPresent()) {
            return presentationService.convertBooksForShellPresentation(Collections.singletonList(byId.get()));
        } else {
            return "Книга с указанным id отсутствует в БД";
        }
    }

    @Transactional
    public String addNewBook(String bookName, String authorName, String genreName) {
        bookRepository.save(new Book(null, bookName, authorName, genreName));
        return "Книга успешно добавлена";
    }

    @Transactional
    public String updateBook(String id, String newBookName, String newAuthor, String newGenre, String newComment) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()) {
            return "Книга с указанным id отсутствует в БД";
        } else {
            Book book = optionalBook.get();
            if (book.getName().equalsIgnoreCase(newBookName)
                    && book.getAuthor().equalsIgnoreCase(newAuthor)
                    && book.getGenre().equalsIgnoreCase(newGenre)) {
                return "Книги с указанным id уже имеет указанные атрибуты. Изменения не требуются.";
            }

            commentRepository.save(new Comment(id, newComment));
            bookRepository.save(new Book(id, newBookName, newAuthor, newGenre));
            return "Книга успешно обновлена";
        }
    }
}
