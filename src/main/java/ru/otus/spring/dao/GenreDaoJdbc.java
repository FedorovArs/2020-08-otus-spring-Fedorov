package ru.otus.spring.dao;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GenreDaoJdbc implements BaseDao<Genre> {

    private final NamedParameterJdbcOperations namedJdbc;
    private final ResultSetExtractor<Optional<Genre>> resultSetExtractor;
    private final RowMapper<Genre> rowMapper;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedJdbc = namedParameterJdbcOperations;

        this.resultSetExtractor = (resultSet) -> {
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                return Optional.of(new Genre(resultSet.getInt("id"), resultSet.getString("name")));
            }
        };

        this.rowMapper = (resultSet, i) ->
                new Genre(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public int count() {
        return namedJdbc.queryForObject("SELECT COUNT(*) FROM GENRES", Collections.emptyMap(), Integer.class);
    }

    @Override
    public void insert(String name) {
        namedJdbc.update("INSERT INTO GENRES(name) VALUES :name", Map.of("name", name));
    }

    @Override
    public Optional<Genre> getById(int id) {
        return namedJdbc.query("SELECT * FROM GENRES WHERE id = :id", Map.of("id", id), this.resultSetExtractor);
    }

    @Override
    public Optional<Genre> getByNameIgnoreCase(String name) {
        return namedJdbc.query("SELECT * FROM GENRES WHERE lower(name) = :name", Map.of("name", name.toLowerCase()), this.resultSetExtractor);
    }

    @Override
    public List<Genre> getAll() {
        return namedJdbc.query("SELECT * FROM GENRES", Collections.emptyMap(), this.rowMapper);
    }

    @Override
    public void deleteById(int id) {
        namedJdbc.update("DELETE FROM GENRES WHERE id = :id", Map.of("id", id));
    }

    @Override
    public void updateById(int id, String name) {
        namedJdbc.update("UPDATE GENRES SET name = :name WHERE id = :id", Map.of("id", id, "name", name));
    }
}
