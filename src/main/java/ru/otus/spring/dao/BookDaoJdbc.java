package ru.otus.spring.dao;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BaseDao<Book> {

    private final NamedParameterJdbcOperations namedJdbc;
    private final ResultSetExtractor<Optional<Book>> resultSetExtractor;
    private final RowMapper<Book> rowMapper;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedJdbc = namedParameterJdbcOperations;

        this.resultSetExtractor = (resultSet) -> {
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                return Optional.of(new Book(resultSet.getInt("id"), resultSet.getString("name")));
            }
        };

        this.rowMapper = (resultSet, i) ->
                new Book(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public int count() {
        return namedJdbc.queryForObject("SELECT COUNT(*) FROM BOOKS", Collections.emptyMap(), Integer.class);
    }

    @Override
    public void insert(String bookName) {
        namedJdbc.update("INSERT INTO BOOKS(name) VALUES :name", Map.of("name", bookName));
    }

    @Override
    public Optional<Book> getById(int id) {
        return namedJdbc.query("SELECT * FROM BOOKS WHERE id = :id", Map.of("id", id), this.resultSetExtractor);
    }

    @Override
    public Optional<Book> getByNameIgnoreCase(String name) {
        return namedJdbc.query("SELECT * FROM BOOKS WHERE lower(name) = :name", Map.of("name", name.toLowerCase()), this.resultSetExtractor);
    }

    @Override
    public List<Book> getAll() {
        return namedJdbc.query("SELECT * FROM BOOKS", Collections.emptyMap(), this.rowMapper);
    }

    @Override
    public void deleteById(int id) {
        namedJdbc.update("DELETE FROM BOOKS WHERE id = :id", Map.of("id", id));
    }

    @Override
    public void updateById(int id, String name) {
        namedJdbc.update("UPDATE BOOKS SET name = :name WHERE id = :id", Map.of("id", id, "name", name));
    }
}
