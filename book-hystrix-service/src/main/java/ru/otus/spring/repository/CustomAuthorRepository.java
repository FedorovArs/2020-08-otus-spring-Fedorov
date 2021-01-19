package ru.otus.spring.repository;

import ru.otus.spring.entity.Author;


public interface CustomAuthorRepository {
    Author getByNameOrCreate(Author author);
}
