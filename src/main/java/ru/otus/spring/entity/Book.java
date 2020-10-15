package ru.otus.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collation = "books")
public class Book {

    @Id
    private String id;

    private String name;

    private String author;

    private String genre;

    public Book(String name, String author, String genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return '(' +
                "id=" + id +
                ", название='" + name +
                ", автор=" + author +
                ", жанр=" + genre +
                ')';
    }
}
