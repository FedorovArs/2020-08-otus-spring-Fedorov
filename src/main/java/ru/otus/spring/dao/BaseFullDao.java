package ru.otus.spring.dao;

import java.util.List;
import java.util.Optional;

public interface BaseFullDao<T, V> {

    int count();

    void insert(T t);

    Optional<T> getById(int id);

    Optional<V> getViewById(int id);

    List<T> getAll();

    List<V> getAllView();

    void deleteById(int id);

    void update(T book);

    Optional<V> findBookViewByNameAndAuthorAndGenre(String name, String author, String genre);
}
