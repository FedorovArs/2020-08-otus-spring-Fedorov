package ru.otus.spring.dao;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorDaoJdbc implements BaseDao<Author> {

    private final NamedParameterJdbcOperations namedJdbc;
    private final ResultSetExtractor<Optional<Author>> resultSetExtractor;
    private final RowMapper<Author> rowMapper;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedJdbc = namedParameterJdbcOperations;

        this.resultSetExtractor = (resultSet) -> {
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                return Optional.of(new Author(resultSet.getInt("id"), resultSet.getString("name")));
            }
        };

        this.rowMapper = (resultSet, i) ->
                new Author(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public int count() {
        return namedJdbc.queryForObject("SELECT COUNT(*) FROM AUTHORS", Collections.emptyMap(), Integer.class);
    }

    @Override
    public void insert(String name) {
        namedJdbc.update("INSERT INTO AUTHORS(name) VALUES :name", Map.of("name", name));
    }

    @Override
    public Optional<Author> getById(int id) {
        return namedJdbc.query("SELECT * FROM AUTHORS WHERE id = :id", Map.of("id", id), this.resultSetExtractor);
    }

    @Override
    public Optional<Author> getByNameIgnoreCase(String name) {
        return namedJdbc.query("SELECT * FROM AUTHORS WHERE lower(name) = :name", Map.of("name", name.toLowerCase()), this.resultSetExtractor);
    }

    @Override
    public List<Author> getAll() {
        return namedJdbc.query("SELECT * FROM AUTHORS", Collections.emptyMap(), rowMapper);
    }

    @Override
    public void deleteById(int id) {
        namedJdbc.update("DELETE FROM AUTHORS WHERE id = :id", Map.of("id", id));
    }

    @Override
    public void updateById(int id, String name) {
        namedJdbc.update("UPDATE AUTHORS SET name = :name WHERE id = :id", Map.of("id", id, "name", name));
    }
}
