package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.io.InputStream;
import java.util.List;

public interface ParseService {

    List<String> parseCsvResource(InputStream resource);

    Question parseQuestionRow(String[] row);
}
