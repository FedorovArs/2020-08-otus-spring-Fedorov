package ru.otus.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collation = "comments")
public class Comment {

    @Id
    private String id;
    private String bookId;
    private String text;

    public Comment(String bookId, String text) {
        this.bookId = bookId;
        this.text = text;
    }
}
