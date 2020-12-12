package ru.otus.spring.entity.nosql;

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
public class MongoBook {

    @Id
    private String id;

    private String name;

    private String author;

    private String genre;

    public MongoBook(String name, String author, String genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}
