package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookFull {
    // Тип id Integer выбран осознанно, в некоторых местех он null
    private Integer id;
    private int authorNameId;
    private int bookNameId;
    private int genreNameId;
}
