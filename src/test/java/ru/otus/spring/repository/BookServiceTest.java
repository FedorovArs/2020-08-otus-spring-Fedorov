package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Comment;
import ru.otus.spring.events.MongoCommentsCascadeDeleteEventsListener;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.BookServiceImpl;
import ru.otus.spring.service.PresentationServiceImpl;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@DisplayName("BookService должен ")
@Import({BookServiceImpl.class, PresentationServiceImpl.class, MongoCommentsCascadeDeleteEventsListener.class})
public class BookServiceTest {

    public static final String NO_COMMENTS = "Комментарии отсутствуют";
    public static final int DEFAULT_BOOKS_COUNT = 4;
    public static final String EXISTING_BOOK_ID = "Book #2";
    public static final String NOT_EXISTING_BOOK_ID = "Book #1000";
    public static final String NOT_EXISTING_BOOK_MSG = "Книга с указанным id отсутствует в БД";
    private static final String SUCCESSFUL_ADDED_BOOK_MSG = "Книга успешно добавлена";
    public static final String NO_CHANGES_MSG = "Книги с указанным id уже имеет указанные атрибуты. Изменения не требуются.";
    public static final String UPDATE_BOOK_SUCCESSFUL_MSG = "Книга успешно обновлена";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookService bookService;


    @DisplayName(" возвращать корректный список книг, соответсвующий данным в БД")
    @Test
    void shouldReturnCorrectBooksCount() {
        List<String> booksStringInfo = getAllBooksFromServiceAndSplitToList();
        List<Book> booksDocuments = bookRepository.findAll();

        assertThat(booksStringInfo).size().isEqualTo(booksDocuments.size());
        for (int i = 0; i < DEFAULT_BOOKS_COUNT; i++) {
            assertBookInfoAndComments(booksStringInfo.get(i), booksDocuments.get(i));
        }
    }

    @DisplayName(" возвращать книгу по существующему id")
    @Test
    void shouldReturnCorrectBookById() {
        String bookStringInfo = bookService.getById(EXISTING_BOOK_ID);
        Optional<Book> optionalBook = bookRepository.findById(EXISTING_BOOK_ID);
        assertThat(optionalBook).isNotEmpty();
        assertBookInfoAndComments(bookStringInfo, optionalBook.get());
    }

    @DisplayName(" возвращать корректное сообщение если книга запрашивается по несуществующему id")
    @Test
    void shouldReturnCurrentMessageIfBookIdIsNotExisting() {
        String bookStringInfo = bookService.getById(NOT_EXISTING_BOOK_ID);
        assertThat(bookStringInfo).isEqualTo(NOT_EXISTING_BOOK_MSG);
    }

    @DisplayName(" создавать новую книгу")
    @Test
    void shouldCreateNewBook() {
        long booksCountBeforeAddBook = bookRepository.count();
        String name = "Три мушкетёра";
        String author = "Александр Дюма";
        String genre = "Приключения";
        String successfulMsg = bookService.addNewBook(name, author, genre);
        long booksCountAfterAddBook = bookRepository.count();

        assertThat(successfulMsg).isEqualTo(SUCCESSFUL_ADDED_BOOK_MSG);
        assertThat(booksCountAfterAddBook).isGreaterThan(booksCountBeforeAddBook);

        Optional<Book> createdBook = bookRepository.findByName(name);
        assertThat(createdBook).isNotEmpty();

        String bookStringInfo = bookService.getById(createdBook.get().getId());

        assertBookInfoAndComments(bookStringInfo, createdBook.get());
    }

    @DisplayName(" выдавать сообщение, что невозможно обновить несуществующую книгу")
    @Test
    void shouldReturnCurrentMessageAndDontUpdateBookIfBookIdIsNotExisting() {
        String updateBookOperationInfo = bookService.updateBook(NOT_EXISTING_BOOK_ID, "fakeBookName", "fakeAuthor", "fakeGenre", "fakeComment");
        assertThat(updateBookOperationInfo).isEqualTo(NOT_EXISTING_BOOK_MSG);
    }

    @DisplayName(" выдавать сообщение, о том, что книга не будет обновлена, т.к. изменения отсутствуют")
    @Test
    void shouldReturnCurrentMessageAndDontUpdateBookIfBookDoesNotHaveChanges() {
        Book existingBook = getExistingBook(null);

        String updateBookOperationInfo = bookService.updateBook(existingBook.getId(),
                existingBook.getName(),
                existingBook.getAuthor(),
                existingBook.getGenre(),
                null);
        assertThat(updateBookOperationInfo).isEqualTo(NO_CHANGES_MSG);
    }

    @DisplayName(" обновить существующую книгу")
    @Test
    void shouldUpdateExistingBook() {
        Book existingBookBeforeUpdate = getExistingBook(null);
        List<Comment> existingBookCommentsBeforeUpdate = commentRepository.findAllByBookId(existingBookBeforeUpdate.getId());

        String newName = "Три мушкетёра";
        String newAuthor = "Александр Дюма";
        String newGenre = "Приключения";
        String newComment = "Невероятно интересная книга";
        String updateBookOperationInfo = bookService.updateBook(existingBookBeforeUpdate.getId(), newName, newAuthor, newGenre, newComment);

        assertThat(updateBookOperationInfo).isEqualTo(UPDATE_BOOK_SUCCESSFUL_MSG);

        Book existingBookAfterUpdate = getExistingBook(null);
        List<Comment> existingBookCommentsAfterUpdate = commentRepository.findAllByBookId(existingBookAfterUpdate.getId());

        assertThat(existingBookAfterUpdate.getId()).isEqualTo(existingBookBeforeUpdate.getId());
        // Проверяем, что поля изменились
        assertThat(existingBookAfterUpdate.getName()).isNotEqualTo(existingBookBeforeUpdate.getName());
        assertThat(existingBookAfterUpdate.getAuthor()).isNotEqualTo(existingBookBeforeUpdate.getAuthor());
        assertThat(existingBookAfterUpdate.getGenre()).isNotEqualTo(existingBookBeforeUpdate.getGenre());

        // Проверяем, что поля изменились именно на указанные нами значения
        assertThat(existingBookAfterUpdate.getName()).isEqualTo(newName);
        assertThat(existingBookAfterUpdate.getAuthor()).isEqualTo(newAuthor);
        assertThat(existingBookAfterUpdate.getGenre()).isEqualTo(newGenre);

        // Проверяем, что добавился комментарий
        assertThat(existingBookCommentsAfterUpdate).hasSizeGreaterThan(existingBookCommentsBeforeUpdate.size());
        assertThat(existingBookCommentsAfterUpdate).anyMatch(s -> s.getText().equals(newComment));
    }

    private Book getExistingBook(@Nullable String existingId) {
        if (existingId == null || existingId.isBlank()) {
            existingId = EXISTING_BOOK_ID;
        }

        Optional<Book> optionalBook = bookRepository.findById(existingId);
        assertThat(optionalBook).isNotEmpty();
        return optionalBook.get();
    }

    @DisplayName(" удалять книгу по Id и все её комментарии")
    @Test
    void shouldDeleteBookByIdThisComments() {
        List<String> booksStringInfoBeforeDelete = getAllBooksFromServiceAndSplitToList();
        List<Book> booksBeforeDelete = bookRepository.findAll();
        String bookIdForDelete = booksBeforeDelete.get(0).getId();
        List<Comment> bookCommentsBeforeDelete = commentRepository.findAllByBookId(bookIdForDelete);

        assertThat(booksStringInfoBeforeDelete).anyMatch(s -> s.contains(bookIdForDelete));
        assertThat(bookCommentsBeforeDelete).isNotEmpty();
        bookService.deleteById(bookIdForDelete);

        List<String> booksStringInfoAfterDelete = getAllBooksFromServiceAndSplitToList();
        List<Book> booksAfterDelete = bookRepository.findAll();
        List<Comment> bookCommentsAfterDelete = commentRepository.findAllByBookId(bookIdForDelete);

        assertThat(booksStringInfoAfterDelete).doesNotContain(bookIdForDelete);
        assertThat(booksAfterDelete).noneMatch(s -> s.getId().equals(bookIdForDelete));
        assertThat(booksStringInfoAfterDelete).hasSizeLessThan(booksStringInfoBeforeDelete.size());
        assertThat(booksAfterDelete).hasSizeLessThan(booksBeforeDelete.size());
        assertThat(bookCommentsAfterDelete).isEmpty();
    }

    private void assertBookInfoAndComments(String bookInfo, Book book) {
        List<Comment> bookComments = commentRepository.findAllByBookId(book.getId());
        assertThat(bookInfo).contains(book.getId())
                .contains(book.getAuthor())
                .contains(book.getGenre())
                .contains(book.getName());

        if (bookComments.isEmpty()) {
            assertThat(bookInfo).contains(NO_COMMENTS);
        } else {
            bookComments.forEach(s -> assertThat(bookInfo).contains(s.getText()));
        }
    }

    private List<String> getAllBooksFromServiceAndSplitToList() {
        String allBooksInOneRow = bookService.getAll();
        return List.of(allBooksInOneRow.split("\n"));
    }
}
