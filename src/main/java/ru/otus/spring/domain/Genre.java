package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Genre {
    private Long id;
    private String name;
}
