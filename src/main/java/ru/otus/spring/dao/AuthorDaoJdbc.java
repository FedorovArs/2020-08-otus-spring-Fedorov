package ru.otus.spring.dao;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedJdbc;
    private final ResultSetExtractor<Optional<Author>> resultSetExtractor;
    private final RowMapper<Author> rowMapper;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedJdbc = namedParameterJdbcOperations;

        this.resultSetExtractor = (resultSet) -> !resultSet.next() ? Optional.empty() : Optional.of(new Author(resultSet.getLong("id"), resultSet.getString("name")));

        this.rowMapper = (resultSet, i) ->
                new Author(resultSet.getLong("id"), resultSet.getString("name"));
    }

    public int count() {
        return namedJdbc.queryForObject("SELECT COUNT(*) FROM AUTHORS", Collections.emptyMap(), Integer.class);
    }

    public long insert(Author author) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", author.getName());

        namedJdbc.update("INSERT INTO AUTHORS(name) VALUES :name", parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Optional<Author> getById(long id) {
        return namedJdbc.query("SELECT * FROM AUTHORS WHERE id = :id", Map.of("id", id), this.resultSetExtractor);
    }

    public Optional<Author> getByNameIgnoreCase(Author author) {
        return namedJdbc.query("SELECT * FROM AUTHORS WHERE lower(name) = :name", Map.of("name", author.getName().toLowerCase()), this.resultSetExtractor);
    }

    public List<Author> getAll() {
        return namedJdbc.query("SELECT * FROM AUTHORS", Collections.emptyMap(), rowMapper);
    }

    public void deleteById(long id) {
        namedJdbc.update("DELETE FROM AUTHORS WHERE id = :id", Map.of("id", id));
    }

    public void updateById(Author author) {
        namedJdbc.update("UPDATE AUTHORS SET name = :name WHERE id = :id", Map.of("id", author.getId(), "name", author.getName()));
    }

    public Author getByNameOrCreate(Author author) {
        return this.getByNameIgnoreCase(author).orElseGet(() -> {
            long id = this.insert(author);
            return this.getById(id).get();
        });
    }
}
