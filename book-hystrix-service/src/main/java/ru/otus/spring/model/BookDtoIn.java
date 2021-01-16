package ru.otus.spring.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Getter
@Setter
public class BookDtoIn {
    private String name;
    private String author;
    private String genre;
}
