import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.dao.ResourceLoader;
import ru.otus.spring.service.DataProducer;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
class TestServiceTest {

    private static final int DEFAULT_PASSING_SCORE = 75;
    public static final int DEFAULT_QUESTIONS_COUNT = 5;

    @Autowired
    private ru.otus.spring.service.TestService testService;

    @Autowired
    private DataProducer questionService;

    @Autowired
    private ResourceLoader resourceLoader;


    @Test
    public void defaultPassingScoreShouldBeIs75() {
        assertEquals(testService.getMinSuccessfulValue(), DEFAULT_PASSING_SCORE);
    }

    @Test
    public void defaultNumberOfQuestionsShouldBeIs5() {
        assertEquals(questionService.getQuestionsList().size(), DEFAULT_QUESTIONS_COUNT);
    }

    @Test
    public void defaultLocaleMustBeRuRu() {
        assertEquals(new Locale("ru", "RU"), resourceLoader.getLocale());
    }
}
