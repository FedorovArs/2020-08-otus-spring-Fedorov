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
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedJdbc;
    private final ResultSetExtractor<Optional<Genre>> resultSetExtractor;
    private final RowMapper<Genre> rowMapper;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedJdbc = namedParameterJdbcOperations;

        this.resultSetExtractor = (resultSet) -> {
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                return Optional.of(new Genre(resultSet.getLong("id"), resultSet.getString("name")));
            }
        };

        this.rowMapper = (resultSet, i) ->
                new Genre(resultSet.getLong("id"), resultSet.getString("name"));
    }

    public int count() {
        return namedJdbc.queryForObject("SELECT COUNT(*) FROM GENRES", Collections.emptyMap(), Integer.class);
    }

    public void insert(Genre genre) {
        namedJdbc.update("INSERT INTO GENRES(name) VALUES :name", Map.of("name", genre.getName()));
    }

    public Optional<Genre> getById(int id) {
        return namedJdbc.query("SELECT * FROM GENRES WHERE id = :id", Map.of("id", id), this.resultSetExtractor);
    }

    public Optional<Genre> getByNameIgnoreCase(Genre genre) {
        return namedJdbc.query("SELECT * FROM GENRES WHERE lower(name) = :name", Map.of("name", genre.getName().toLowerCase()), this.resultSetExtractor);
    }

    public List<Genre> getAll() {
        return namedJdbc.query("SELECT * FROM GENRES", Collections.emptyMap(), this.rowMapper);
    }

    public void deleteById(int id) {
        namedJdbc.update("DELETE FROM GENRES WHERE id = :id", Map.of("id", id));
    }

    public void updateById(Genre genre) {
        namedJdbc.update("UPDATE GENRES SET name = :name WHERE id = :id", Map.of("id", genre.getId(), "name", genre.getName()));
    }

    public Genre getByNameOrCreate(Genre genre) {
        Optional<Genre> genreFromDb = this.getByNameIgnoreCase(genre);
        if (genreFromDb.isEmpty()) {
            this.insert(genre);
            genreFromDb = this.getByNameIgnoreCase(genre);
        }
        return genreFromDb.get();
    }
}
