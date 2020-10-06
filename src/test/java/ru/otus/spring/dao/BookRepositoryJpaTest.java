package ru.otus.spring.dao;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.entity.Author;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Comment;
import ru.otus.spring.entity.Genre;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@DisplayName("DAO для работы с книгами должно:")
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    public static final int DEFAULT_ROW_COUNT_AT_START = 4;
    public static final int FAKE_ID = 1000;
    private static final long EXPECTED_QUERIES_COUNT = 2;
    private static final String CLASSIC_AUTHOR = "А.С. Пушкин";
    private static final String CLASSIC_GENRE = "Классика";
    private static final String EXCELLENT_BOOK_COMMENT = "Великолепная книга";
    private static final String BAD_BOOK_COMMENT = "Ужасная книга";
    private static final String BOOK_NAME = "Евгений Онегин";
    private static final long FIRST_BOOK_ID = 1;
    public static final String NEW_TEST_BOOK_NAME = "Новое тестовое имя";


    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("отдавать кол-во записей в таблице")
    @Test
    void shouldGetCountRows() {
        long jpaCount = bookRepositoryJpa.count();
        Query query = em.getEntityManager().createNativeQuery("SELECT COUNT(*) FROM BOOKS");
        long emCount = ((Number) query.getSingleResult()).longValue();
        assertThat(jpaCount).isEqualTo(emCount);
    }

    @DisplayName("возвращать одинаковое кол-во записей методом getAll")
    @Test
    void shouldReturnSameNumberOfRecords() {
        Query queryAllRows = em.getEntityManager().createNativeQuery("SELECT * FROM BOOKS");
        int emAllRowsCount = queryAllRows.getResultList().size();
        List<Book> all = bookRepositoryJpa.findAll();
        assertThat(all).hasSize(emAllRowsCount);
    }

    @DisplayName(" должен корректно сохранять всю информацию о книге")
    @Test
    void shouldSaveAllBookInfo() {
        var avatar = new Author(null, CLASSIC_AUTHOR);
        var genre = new Genre(null, CLASSIC_GENRE);

        var newBook = new Book(null, BOOK_NAME, avatar, genre);
        // Сохранение новой книги
        bookRepositoryJpa.save(newBook);
        assertThat(newBook.getId()).isNotNull().isGreaterThan(0);
        assertThat(newBook.getComments()).isNullOrEmpty();


        var goodComment = new Comment(null, newBook, EXCELLENT_BOOK_COMMENT);
        var badComment = new Comment(null, newBook, BAD_BOOK_COMMENT);
        List<Comment> comments = new ArrayList<>();
        comments.add(goodComment);
        comments.add(badComment);
        newBook.setComments(comments);

        // Обновление существующей книги
        bookRepositoryJpa.save(newBook);

        assertAll(() -> {
            assertThat(newBook.getComments()).hasSize(comments.size());
            assertThat(newBook.getComments().get(0).getId()).isNotNull().isGreaterThan(0);
            assertThat(newBook.getComments().get(1).getId()).isNotNull().isGreaterThan(0);
            assertThat(newBook.getComments().get(0).getText()).isEqualTo(EXCELLENT_BOOK_COMMENT);
            assertThat(newBook.getComments().get(1).getText()).isEqualTo(BAD_BOOK_COMMENT);
        });

        val expectedBook = em.find(Book.class, newBook.getId());
        assertThat(expectedBook).isEqualToComparingFieldByField(newBook);
    }

    @DisplayName(" должен загружать информацию о нужном студенте по его id")
    @Test
    void shouldFindExpectedBookById() {
        List<Book> allBooks = bookRepositoryJpa.findAll();
        Book firstBook = allBooks.get(0);

        var optionalBook = bookRepositoryJpa.findById(firstBook.getId());
        var expectedBook = em.find(Book.class, firstBook.getId());
        assertThat(optionalBook).isPresent().get()
                .isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        var allBooks = bookRepositoryJpa.findAll();
        var bookForDelete = allBooks.get(0);
        bookRepositoryJpa.deleteById(bookForDelete.getId());

        var allBooksAfterDelete = bookRepositoryJpa.findAll();
        assertAll(() -> {
            assertThat(allBooksAfterDelete).hasSizeLessThan(allBooks.size());
            assertThat(allBooksAfterDelete)
                    .noneMatch(row -> row.getId().equals(bookForDelete.getId()));
        });
    }

    @DisplayName("не должен удалять книгу если id отсутствует в БД")
    @Test
    void shouldNotDeleteBookIfIdIsNotInDatabase() {
        var allBooksBefore = bookRepositoryJpa.findAll();
        bookRepositoryJpa.deleteById(FAKE_ID);
        var allBooksAfter = bookRepositoryJpa.findAll();

        assertAll(() -> {
            assertThat(allBooksBefore.size()).isEqualTo(allBooksAfter.size());
            assertThat(allBooksBefore).isEqualTo(allBooksAfter);
        });
    }

    @DisplayName("должен добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        var allBooksBefore = bookRepositoryJpa.findAll();
        var bookName = allBooksBefore.get(1).getName();
        var author = allBooksBefore.get(0).getAuthor();
        var genre = allBooksBefore.get(2).getGenre();

        Book bookForSave = new Book(null, bookName, author, genre);
        bookRepositoryJpa.save(bookForSave);
        var allBooksAfter = bookRepositoryJpa.findAll();

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
        var allBooksBefore = bookRepositoryJpa.findAll();

        var bookName = allBooksBefore.get(1).getName();
        var author = allBooksBefore.get(1).getAuthor();
        var genre = allBooksBefore.get(2).getGenre();
        var comments = allBooksBefore.get(1).getComments();

        Book bookForUpdate = allBooksBefore.get(0);

        assertAll(() -> {
            assertThat(bookForUpdate.getName()).isNotEqualTo(bookName);
            assertThat(bookForUpdate.getAuthor()).isNotEqualTo(author);
            assertThat(bookForUpdate.getGenre()).isNotEqualTo(genre);
            assertThat(bookForUpdate.getComments()).isNotEqualTo(comments);
        });

        bookForUpdate.setName(bookName);
        bookForUpdate.setAuthor(author);
        bookForUpdate.setGenre(genre);
        bookForUpdate.setComments(comments);
        bookRepositoryJpa.updateById(bookForUpdate);

        Optional<Book> bookAfterUpdate = bookRepositoryJpa.findById(bookForUpdate.getId());

        assertAll(() -> {
            assertThat(bookAfterUpdate.isPresent()).isTrue();
            assertThat(bookAfterUpdate.get().getName()).isEqualTo(bookName);
            assertThat(bookAfterUpdate.get().getAuthor()).isEqualTo(author);
            assertThat(bookAfterUpdate.get().getGenre()).isEqualTo(genre);
            assertThat(bookAfterUpdate.get().getComments()).isEqualTo(comments);
        });
    }

    @DisplayName("Проверка решения проблемы N+1")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        // Выполняется первый запрос к БД
        var books = bookRepositoryJpa.findAll();
        // При обращении к LAZY полю происходит второй запрос к БД
        String booksToString = books.stream()
                .map(Book::toString)
                .collect(Collectors.joining(", "));

        assertThat(booksToString).contains("Комментарии отсутствуют");
        assertThat(booksToString).contains("Хорошая книга");
        assertThat(books).isNotNull().hasSize(DEFAULT_ROW_COUNT_AT_START);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName(" должен изменять имя заданной книги по её id")
    @Test
    void shouldUpdateBookNameById() {
        var firstBook = em.find(Book.class, FIRST_BOOK_ID);
        String oldName = firstBook.getName();
        em.detach(firstBook);

        firstBook.setName(NEW_TEST_BOOK_NAME);
        bookRepositoryJpa.updateById(firstBook);
        var updatedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(updatedBook.getName()).isNotEqualTo(oldName).isEqualTo(NEW_TEST_BOOK_NAME);
    }

    @DisplayName(" должен удалить книгу по её id")
    @Test
    void shouldDeleteBookNameById() {
        val firstBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(firstBook).isNotNull();
        em.detach(firstBook);

        bookRepositoryJpa.deleteById(FIRST_BOOK_ID);
        val deletedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(deletedBook).isNull();
    }
}