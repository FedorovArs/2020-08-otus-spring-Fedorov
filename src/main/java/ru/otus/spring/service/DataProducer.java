package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface DataProducer {

    String DEFAULT_DELIMITER = ";";

    List<Question> getQuestionsList();

    String getLocalizeMsg(String msgKey);
}
