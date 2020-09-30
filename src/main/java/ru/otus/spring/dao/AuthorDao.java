package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

// Методов объявлено больше чем используется, что бы попрактиковаться и набить руку.
public interface AuthorDao {

    public int count();

    long insert(Author author);

    Optional<Author> getById(long id);

    Optional<Author> getByNameIgnoreCase(Author author);

    List<Author> getAll();

    void deleteById(long id);

    void updateById(Author author);

    Author getByNameOrCreate(Author author);
}
