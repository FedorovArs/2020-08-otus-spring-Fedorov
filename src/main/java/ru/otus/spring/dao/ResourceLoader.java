package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.util.List;
import java.util.Locale;

public interface ResourceLoader {

    List<Question> getQuestionsList();

    Locale getLocale();
}
