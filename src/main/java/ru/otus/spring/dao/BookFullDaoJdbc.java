package ru.otus.spring.dao;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.BookFull;
import ru.otus.spring.domain.BookFullView;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookFullDaoJdbc implements BaseFullDao<BookFull, BookFullView> {

    private final NamedParameterJdbcOperations namedJdbc;
    private final ResultSetExtractor<Optional<BookFull>> fullBookResultSetExtractor;
    private final ResultSetExtractor<Optional<BookFullView>> fullBookViewResultSetExtractor;
    private final RowMapper<BookFull> fullBookRowMapper;
    private final RowMapper<BookFullView> bookFullViewRowMapper;

    public BookFullDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedJdbc = namedParameterJdbcOperations;

        this.fullBookResultSetExtractor = (resultSet) -> {
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                return Optional.of(new BookFull(
                        resultSet.getInt("id"),
                        resultSet.getInt("author_name_id"),
                        resultSet.getInt("book_name_id"),
                        resultSet.getInt("genre_name_id")
                ));
            }
        };

        this.fullBookViewResultSetExtractor = (resultSet) -> {
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                return Optional.of(new BookFullView(
                        resultSet.getInt("id"),
                        resultSet.getString("book_name"),
                        resultSet.getString("author_name"),
                        resultSet.getString("genre_name"))
                );
            }
        };

        this.fullBookRowMapper = (resultSet, i) ->
                new BookFull(resultSet.getInt("id"),
                        resultSet.getInt("author_name_id"),
                        resultSet.getInt("book_name_id"),
                        resultSet.getInt("genre_name_id"));

        this.bookFullViewRowMapper = (resultSet, i) ->
                new BookFullView(resultSet.getInt("id"),
                        resultSet.getString("book_name"),
                        resultSet.getString("author_name"),
                        resultSet.getString("genre_name"));
    }

    @Override
    public int count() {
        return namedJdbc.queryForObject("SELECT COUNT(*) FROM BOOKS_FULL", Collections.emptyMap(), Integer.class);
    }

    @Override
    public void insert(BookFull bookFull) {

        namedJdbc.update("INSERT INTO BOOKS_FULL (author_name_id, book_name_id, genre_name_id) VALUES (:authorNameId, :bookNameId, :genreNameId)",
                Map.of(
                        "authorNameId", bookFull.getAuthorNameId(),
                        "bookNameId", bookFull.getBookNameId(),
                        "genreNameId", bookFull.getGenreNameId()
                ));
    }

    @Override
    public Optional<BookFull> getById(int id) {
        return namedJdbc.query("SELECT * FROM BOOKS_FULL WHERE id = :id", Map.of("id", id), this.fullBookResultSetExtractor);
    }

    @Override
    public Optional<BookFullView> getViewById(int id) {
        return namedJdbc.query("SELECT * FROM BOOKS_FULL_VIEW WHERE id = :id", Map.of("id", id), this.fullBookViewResultSetExtractor);
    }

    @Override
    public List<BookFull> getAll() {
        return namedJdbc.query("SELECT * FROM BOOKS_FULL", Collections.emptyMap(), this.fullBookRowMapper);
    }

    @Override
    public List<BookFullView> getAllView() {
        return namedJdbc.query("SELECT * FROM BOOKS_FULL_VIEW", Collections.emptyMap(), this.bookFullViewRowMapper);
    }

    @Override
    public void deleteById(int id) {
        namedJdbc.update("DELETE FROM BOOKS_FULL WHERE id = :id", Map.of("id", id));
    }

    @Override
    public Optional<BookFullView> findBookViewByNameAndAuthorAndGenre(String name, String author, String genre) {
        return namedJdbc.query("SELECT * FROM BOOKS_FULL_VIEW " +
                        "WHERE lower(book_name) = :name and lower(author_name) = :author and lower(genre_name) = :genre",
                Map.of("name", name.toLowerCase(), "author", author.toLowerCase(), "genre", genre.toLowerCase()),
                this.fullBookViewResultSetExtractor);
    }

    @Override
    public void update(BookFull book) {
        namedJdbc.update("UPDATE BOOKS_FULL SET author_name_id = :authorNameId, book_name_id = :bookNameId," +
                        " genre_name_id = :genreNameId WHERE id = :id",
                Map.of(
                        "id", book.getId(),
                        "authorNameId", book.getAuthorNameId(),
                        "bookNameId", book.getBookNameId(),
                        "genreNameId", book.getGenreNameId()
                ));
    }
}
