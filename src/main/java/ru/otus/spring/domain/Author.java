package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Author {
    private Long id;
    private String name;
}
