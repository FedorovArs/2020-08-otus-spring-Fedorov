package ru.otus.spring.repository;

import ru.otus.spring.entity.Genre;


public interface CustomGenreRepository {
    Genre getByNameOrCreate(Genre author);
}
