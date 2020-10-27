package ru.otus.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "books")
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
}
