package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

// Методов объявлено больше чем используется, что бы попрактиковаться и набить руку.
public interface AuthorDao {

    public int count();

    void insert(Author author);

    Optional<Author> getById(int id);

    Optional<Author> getByNameIgnoreCase(Author author);

    List<Author> getAll();

    void deleteById(int id);

    void updateById(Author author);

    Author getByNameOrCreate(Author author);
}
