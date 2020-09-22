package ru.otus.spring.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    int count();

    void insert(String bookName);

    Optional<T> getById(int id);

    Optional<T> getByNameIgnoreCase(String name);

    default T getByNameOrCreate(String name) {
        return this.getByNameIgnoreCase(name)
                .orElseGet(() -> {
                    this.insert(name);
                    return this.getByNameIgnoreCase(name).get();
                });
    }

    List<T> getAll();

    void deleteById(int id);

    void updateById(int id, String name);
}
