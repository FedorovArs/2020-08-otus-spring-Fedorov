package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.io.InputStream;
import java.util.List;

public interface ParseService {

    int QUESTION_TYPE_INDEX = 0;
    int QUESTION_TEXT_INDEX = 1;
    int ANSWERS_TEXT_INDEX = 2;
    int CORRECT_ANSWER_TEXT_INDEX = 3;
    String ANSWERS_DEFAULT_DELIMITER = ",";

    List<String> parseCsvResource(InputStream resource);

    Question parseQuestionRow(String[] row);
}
