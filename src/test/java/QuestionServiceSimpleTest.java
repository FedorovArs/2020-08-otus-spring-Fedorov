import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.TestService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)

public class QuestionServiceSimpleTest {

    private static final int DEFAULT_PASSING_SCORE = 75;
    public static final int DEFAULT_QUESTIONS_COUNT = 5;

    @Autowired
    private TestService testService;

    @Autowired
    private QuestionService questionService;


    @Test
    public void defaultPassingScoreShouldBeIs75() {
        assertEquals(testService.getMinSuccessfulValue(), DEFAULT_PASSING_SCORE);
    }

    @Test
    public void defaultNumberOfQuestionsShouldBeIs5() {
        assertEquals(questionService.getQuestionsList().size(), DEFAULT_QUESTIONS_COUNT);
    }
}
