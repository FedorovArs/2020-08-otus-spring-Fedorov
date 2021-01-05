package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.dao.ResourceLoader;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TestServiceSimpleTest {

    private static final int DEFAULT_PASSING_SCORE = 75;
    public static final int DEFAULT_QUESTIONS_COUNT = 5;

    @Autowired
    private ru.otus.spring.service.TestService testService;

    @Autowired
    private ResourceLoader resourceLoader;


    @Test
    public void defaultPassingScoreShouldBeIs75() {
        assertEquals(testService.getMinSuccessfulValue(), DEFAULT_PASSING_SCORE);
    }

    @Test
    public void defaultNumberOfQuestionsShouldBeIs5() {
        assertEquals(resourceLoader.getQuestionsList().size(), DEFAULT_QUESTIONS_COUNT);
    }

    @Test
    public void defaultLocaleMustBeRuRu() {
        assertEquals(new Locale("ru", "RU"), resourceLoader.getLocale());
    }
}
