package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

// Методов объявлено больше чем используется, что бы попрактиковаться и набить руку.
public interface GenreDao {

    int count();

    void insert(Genre genre);

    Optional<Genre> getById(int id);

    Optional<Genre> getByNameIgnoreCase(Genre genre);

    List<Genre> getAll();

    void deleteById(int id);

    void updateById(Genre genre);

    Genre getByNameOrCreate(Genre genre);
}
