package ru.otus.spring.service;

import ru.otus.spring.dao.ResourceLoaderDao;
import ru.otus.spring.domain.Question;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuestionServiceSimple implements QuestionService {

    public static final String DEFAULT_DELIMITER = ";";

    private final ResourceLoaderDao resourceLoaderDao;
    private final ParseServiceSimple parseServiceSimple;

    public QuestionServiceSimple(ResourceLoaderDao resourceLoaderDao, ParseServiceSimple parseServiceSimple) {
        this.resourceLoaderDao = resourceLoaderDao;
        this.parseServiceSimple = parseServiceSimple;
    }

    public List<Question> getQuestionsList() {
        List<Question> result = new ArrayList<>();
        InputStream resource = resourceLoaderDao.getResource();

        List<String> questionRows = parseServiceSimple.parseCsvResource(resource);

        questionRows.forEach(row -> {
            String[] fullQuestionRow = row.split(DEFAULT_DELIMITER);
            Question question = parseServiceSimple.parseQuestionRow(fullQuestionRow);
            result.add(question);
        });

        return result;
    }
}
