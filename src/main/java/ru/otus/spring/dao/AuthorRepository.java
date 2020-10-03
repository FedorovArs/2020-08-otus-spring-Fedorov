package ru.otus.spring.dao;

import ru.otus.spring.entity.Author;

import java.util.List;
import java.util.Optional;

// Методов объявлено больше чем используется, что бы попрактиковаться и набить руку.
public interface AuthorRepository {

    public long count();

    Author save(Author author);

    Optional<Author> findById(long id);

    Optional<Author> getByNameIgnoreCase(Author author);

    List<Author> getAll();

    void deleteById(long id);

    void updateById(Author author);

    Author getByNameOrCreate(Author author);
}
