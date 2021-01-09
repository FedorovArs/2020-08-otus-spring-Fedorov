package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.io.InputStream;
import java.util.List;

public interface QuestionsCsvParser {

    List<String> parseCsvResource(InputStream resource);

    Question parseQuestionRow(String[] row);
}
