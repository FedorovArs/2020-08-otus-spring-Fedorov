package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookFullView {
    private final int id;
    private final String bookName;
    private final String authorName;
    private final String genreName;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", название=" + bookName +
                ", автор=" + authorName +
                ", жанр=" + genreName +
                "}";
    }
}
