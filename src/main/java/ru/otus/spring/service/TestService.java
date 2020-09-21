package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface TestService {

    void runTest(List<Question> questions);

    int getMinSuccessfulValue();
}
