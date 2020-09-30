package ru.otus.spring.dao;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedJdbc;
    private final ResultSetExtractor<Optional<Book>> resultSetExtractor;
    private final RowMapper<Book> rowMapper;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedJdbc = namedParameterJdbcOperations;

        this.resultSetExtractor = (resultSet) -> !resultSet.next() ? Optional.empty() : Optional.of(new Book(resultSet.getLong("id"), resultSet.getString("name"),
                new Author(resultSet.getLong("author_id"), resultSet.getString("author")),
                new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre"))));

        this.rowMapper = (resultSet, i) ->
                new Book(resultSet.getLong("id"), resultSet.getString("name"),
                        new Author(resultSet.getLong("author_id"), resultSet.getString("author")),
                        new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre")));
    }

    public int count() {
        return namedJdbc.queryForObject("SELECT COUNT(*) FROM BOOKS", Collections.emptyMap(), Integer.class);
    }

    public long insert(Book book) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", book.getName());
        parameterSource.addValue("authorId", book.getAuthor().getId());
        parameterSource.addValue("genreId", book.getGenre().getId());

        namedJdbc.update("INSERT INTO BOOKS(name, author_id, genre_id) VALUES (:name, :authorId, :genreId)", parameterSource, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Optional<Book> getById(long id) {
        return namedJdbc.query("SELECT b.id, b.name, a.id as author_id, a.name as author, g.id as genre_id, g.name as genre " +
                "FROM BOOKS b " +
                "LEFT JOIN authors a on b.author_id = a.id " +
                "LEFT JOIN genres g on b.genre_id = g.id WHERE b.id = :id", Map.of("id", id), this.resultSetExtractor);
    }

    public List<Book> getAll() {
        return namedJdbc.query("SELECT b.id, b.name, a.id as author_id, a.name as author, g.id as genre_id, g.name as genre " +
                "FROM BOOKS b " +
                "LEFT JOIN authors a on b.author_id = a.id " +
                "LEFT JOIN genres g on b.genre_id = g.id", Collections.emptyMap(), this.rowMapper);
    }

    public void deleteById(long id) {
        namedJdbc.update("DELETE FROM BOOKS WHERE id = :id", Map.of("id", id));
    }

    public void updateById(Book book) {
        namedJdbc.update("UPDATE BOOKS SET name = :name, author_id = :authorId, genre_id = :genreId WHERE id = :id",
                Map.of("id", book.getId(),
                        "name", book.getName(),
                        "authorId", book.getAuthor().getId(),
                        "genreId", book.getGenre().getId()));
    }
}
