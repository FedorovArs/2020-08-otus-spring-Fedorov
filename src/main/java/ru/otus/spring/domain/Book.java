package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {
    private Long id;
    private String name;
    private Author author;
    private Genre genre;

    @Override
    public String toString() {
        return '(' +
                "id=" + id +
                ", название='" + name +
                ", автор=" + author.getName() +
                ", жанр=" + genre.getName() +
                ')';
    }
}
