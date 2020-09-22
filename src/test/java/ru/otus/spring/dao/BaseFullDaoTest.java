package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.BookFull;
import ru.otus.spring.domain.BookFullView;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@DisplayName("DAO для работы с книгами должно:")
@Import(BookFullDaoJdbc.class)
class BaseFullDaoTest {

    public static final int DEFAULT_ROW_COUNT_AT_START = 4;
    public static final int FAKE_ID = 1000;
    @Autowired
    private BookFullDaoJdbc bookFullDaoJdbc;

    @DisplayName("отдавать кол-во записей в таблице")
    @Test
    void shouldGetCountRows() {
        int count = bookFullDaoJdbc.count();
        assertThat(count).isEqualTo(DEFAULT_ROW_COUNT_AT_START);
    }

    @DisplayName("возвращать одинаковое кол-во записей методом count и getAll")
    @Test
    void shouldReturnSameNumberOfRecords() {
        int count = bookFullDaoJdbc.count();
        List<BookFull> all = bookFullDaoJdbc.getAll();
        assertThat(all).hasSize(count);
    }

    @DisplayName("давать книгу (BookFull) по id")
    @Test
    void shouldGetFullBookById() {
        List<BookFull> allBooks = bookFullDaoJdbc.getAll();
        BookFull firstBook = allBooks.get(0);
        Optional<BookFull> optionalBook = bookFullDaoJdbc.getById(firstBook.getId());
        assertAll(() -> {
            assertThat(optionalBook.isPresent()).isTrue();
            assertThat(optionalBook.get().getId()).isEqualTo(firstBook.getId());
            assertThat(optionalBook.get().getAuthorNameId()).isEqualTo(firstBook.getAuthorNameId());
            assertThat(optionalBook.get().getBookNameId()).isEqualTo(firstBook.getBookNameId());
            assertThat(optionalBook.get().getGenreNameId()).isEqualTo(firstBook.getGenreNameId());
        });
    }

    @DisplayName("давать книгу (BookFullView) по id")
    @Test
    void shouldGetFullViewBookById() {
        List<BookFullView> allBooks = bookFullDaoJdbc.getAllView();
        BookFullView firstBook = allBooks.get(0);
        var optionalBook = bookFullDaoJdbc.getViewById(firstBook.getId());
        assertAll(() -> {
            assertThat(optionalBook.isPresent()).isTrue();
            assertThat(optionalBook.get().getId()).isEqualTo(firstBook.getId());
            assertThat(optionalBook.get().getAuthorName()).isEqualTo(firstBook.getAuthorName());
            assertThat(optionalBook.get().getBookName()).isEqualTo(firstBook.getBookName());
            assertThat(optionalBook.get().getGenreName()).isEqualTo(firstBook.getGenreName());
        });
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        var allBooks = bookFullDaoJdbc.getAll();
        var bookForDelete = allBooks.get(0);
        bookFullDaoJdbc.deleteById(bookForDelete.getId());

        var allBooksAfterDelete = bookFullDaoJdbc.getAll();
        assertAll(() -> {
            assertThat(allBooksAfterDelete).hasSizeLessThan(allBooks.size());
            assertThat(allBooksAfterDelete)
                    .noneMatch(row -> row.getId().equals(bookForDelete.getId()));
        });
    }

    @DisplayName("не должен удалять книгу если id отсутствует в БД")
    @Test
    void shouldNotDeleteBookIfIdIsNotInDatabase() {
        var allBooksBefore = bookFullDaoJdbc.getAll();
        bookFullDaoJdbc.deleteById(FAKE_ID);
        var allBooksAfter = bookFullDaoJdbc.getAll();

        assertAll(() -> {
            assertThat(allBooksBefore.size()).isEqualTo(allBooksAfter.size());
            assertThat(allBooksBefore).isEqualTo(allBooksAfter);
        });
    }

    @DisplayName("должен добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        var allBooksBefore = bookFullDaoJdbc.getAll();
        var authorNameId = allBooksBefore.get(0).getAuthorNameId();
        var bookNameId = allBooksBefore.get(1).getBookNameId();
        var genreNameId = allBooksBefore.get(2).getGenreNameId();

        BookFull bookForSave = new BookFull(null, authorNameId, bookNameId, genreNameId);
        bookFullDaoJdbc.insert(bookForSave);
        var allBooksAfter = bookFullDaoJdbc.getAll();

        assertAll(() -> {
            assertThat(allBooksAfter).hasSizeGreaterThan(allBooksBefore.size());
            allBooksAfter.removeAll(allBooksBefore);
            BookFull savedBook = allBooksAfter.get(0);


            assertThat(savedBook.getAuthorNameId()).isEqualTo(authorNameId);
            assertThat(savedBook.getBookNameId()).isEqualTo(bookNameId);
            assertThat(savedBook.getGenreNameId()).isEqualTo(genreNameId);
        });
    }

    @DisplayName("должен обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        var allBooksBefore = bookFullDaoJdbc.getAll();
        var authorNameId = allBooksBefore.get(1).getAuthorNameId();
        var bookNameId = allBooksBefore.get(1).getBookNameId();
        var genreNameId = allBooksBefore.get(2).getGenreNameId();

        BookFull bookforUpdate = allBooksBefore.get(0);

        assertAll(() -> {
            assertThat(bookforUpdate.getAuthorNameId()).isNotEqualTo(authorNameId);
            assertThat(bookforUpdate.getBookNameId()).isNotEqualTo(bookNameId);
            assertThat(bookforUpdate.getGenreNameId()).isNotEqualTo(genreNameId);
        });

        bookforUpdate.setAuthorNameId(authorNameId);
        bookforUpdate.setBookNameId(bookNameId);
        bookforUpdate.setGenreNameId(genreNameId);
        bookFullDaoJdbc.update(bookforUpdate);

        Optional<BookFull> bookAfterUpdate = bookFullDaoJdbc.getById(bookforUpdate.getId());

        assertAll(() -> {
            assertThat(bookAfterUpdate.isPresent()).isTrue();
            assertThat(bookAfterUpdate.get().getAuthorNameId()).isEqualTo(authorNameId);
            assertThat(bookAfterUpdate.get().getBookNameId()).isEqualTo(bookNameId);
            assertThat(bookAfterUpdate.get().getGenreNameId()).isEqualTo(genreNameId);
        });
    }

    @DisplayName("должен найти БД по названию, имени автора и жанру")
    @Test
    void shouldFindBookByParams() {
        Optional<BookFullView> firstRow = bookFullDaoJdbc.getViewById(1);
        assertThat(firstRow.isPresent()).isTrue();

        String authorName = firstRow.get().getAuthorName();
        String bookName = firstRow.get().getBookName();
        String genreName = firstRow.get().getGenreName();
        Optional<BookFullView> foundBook = bookFullDaoJdbc.findBookViewByNameAndAuthorAndGenre(bookName, authorName, genreName);

        assertAll(() -> {
            assertThat(foundBook.isPresent()).isTrue();
            assertThat(foundBook.get().getId()).isEqualTo(firstRow.get().getId());
            assertThat(foundBook.get().getAuthorName()).isEqualTo(authorName);
            assertThat(foundBook.get().getBookName()).isEqualTo(bookName);
            assertThat(foundBook.get().getGenreName()).isEqualTo(genreName);
        });
    }
}