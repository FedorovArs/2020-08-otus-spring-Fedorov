package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@DisplayName("DAO для работы с книгами должно:")
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    public static final int DEFAULT_ROW_COUNT_AT_START = 4;
    public static final int FAKE_ID = 1000;
    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @DisplayName("отдавать кол-во записей в таблице")
    @Test
    void shouldGetCountRows() {
        int count = bookDaoJdbc.count();
        assertThat(count).isEqualTo(DEFAULT_ROW_COUNT_AT_START);
    }

    @DisplayName("возвращать одинаковое кол-во записей методом count и getAll")
    @Test
    void shouldReturnSameNumberOfRecords() {
        int count = bookDaoJdbc.count();
        List<Book> all = bookDaoJdbc.getAll();
        assertThat(all).hasSize(count);
    }

    @DisplayName("давать книгу (BookFull) по id")
    @Test
    void shouldGetFullBookById() {
        List<Book> allBooks = bookDaoJdbc.getAll();
        Book firstBook = allBooks.get(0);
        Optional<Book> optionalBook = bookDaoJdbc.getById(firstBook.getId());
        assertAll(() -> {
            assertThat(optionalBook.isPresent()).isTrue();
            assertThat(optionalBook.get().getId()).isEqualTo(firstBook.getId());
            assertThat(optionalBook.get().getAuthor()).isEqualTo(firstBook.getAuthor());
            assertThat(optionalBook.get().getName()).isEqualTo(firstBook.getName());
            assertThat(optionalBook.get().getGenre()).isEqualTo(firstBook.getGenre());
        });
    }

    @DisplayName("давать книгу (BookFullView) по id")
    @Test
    void shouldGetFullViewBookById() {
        List<Book> allBooks = bookDaoJdbc.getAll();
        Book firstBook = allBooks.get(0);
        var optionalBook = bookDaoJdbc.getById(firstBook.getId());
        assertAll(() -> {
            assertThat(optionalBook.isPresent()).isTrue();
            assertThat(optionalBook.get().getId()).isEqualTo(firstBook.getId());
            assertThat(optionalBook.get().getAuthor()).isEqualTo(firstBook.getAuthor());
            assertThat(optionalBook.get().getName()).isEqualTo(firstBook.getName());
            assertThat(optionalBook.get().getGenre()).isEqualTo(firstBook.getGenre());
        });
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        var allBooks = bookDaoJdbc.getAll();
        var bookForDelete = allBooks.get(0);
        bookDaoJdbc.deleteById(bookForDelete.getId());

        var allBooksAfterDelete = bookDaoJdbc.getAll();
        assertAll(() -> {
            assertThat(allBooksAfterDelete).hasSizeLessThan(allBooks.size());
            assertThat(allBooksAfterDelete)
                    .noneMatch(row -> row.getId().equals(bookForDelete.getId()));
        });
    }

    @DisplayName("не должен удалять книгу если id отсутствует в БД")
    @Test
    void shouldNotDeleteBookIfIdIsNotInDatabase() {
        var allBooksBefore = bookDaoJdbc.getAll();
        bookDaoJdbc.deleteById(FAKE_ID);
        var allBooksAfter = bookDaoJdbc.getAll();

        assertAll(() -> {
            assertThat(allBooksBefore.size()).isEqualTo(allBooksAfter.size());
            assertThat(allBooksBefore).isEqualTo(allBooksAfter);
        });
    }

    @DisplayName("должен добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        var allBooksBefore = bookDaoJdbc.getAll();
        var bookName = allBooksBefore.get(1).getName();
        var author = allBooksBefore.get(0).getAuthor();
        var genre = allBooksBefore.get(2).getGenre();

        Book bookForSave = new Book(null, bookName, author, genre);
        bookDaoJdbc.insert(bookForSave);
        var allBooksAfter = bookDaoJdbc.getAll();

        assertAll(() -> {
            assertThat(allBooksAfter).hasSizeGreaterThan(allBooksBefore.size());
            allBooksAfter.removeAll(allBooksBefore);
            Book savedBook = allBooksAfter.get(0);


            assertThat(savedBook.getName()).isEqualTo(bookName);
            assertThat(savedBook.getAuthor()).isEqualTo(author);
            assertThat(savedBook.getGenre()).isEqualTo(genre);
        });
    }

    @DisplayName("должен обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        var allBooksBefore = bookDaoJdbc.getAll();

        var bookName = allBooksBefore.get(1).getName();
        var author = allBooksBefore.get(1).getAuthor();
        var genre = allBooksBefore.get(2).getGenre();

        Book bookForUpdate = allBooksBefore.get(0);

        assertAll(() -> {
            assertThat(bookForUpdate.getName()).isNotEqualTo(bookName);
            assertThat(bookForUpdate.getAuthor()).isNotEqualTo(author);
            assertThat(bookForUpdate.getGenre()).isNotEqualTo(genre);
        });

        bookForUpdate.setName(bookName);
        bookForUpdate.setAuthor(author);
        bookForUpdate.setGenre(genre);
        bookDaoJdbc.updateById(bookForUpdate);

        Optional<Book> bookAfterUpdate = bookDaoJdbc.getById(bookForUpdate.getId());

        assertAll(() -> {
            assertThat(bookAfterUpdate.isPresent()).isTrue();
            assertThat(bookAfterUpdate.get().getName()).isEqualTo(bookName);
            assertThat(bookAfterUpdate.get().getAuthor()).isEqualTo(author);
            assertThat(bookAfterUpdate.get().getGenre()).isEqualTo(genre);
        });
    }
}