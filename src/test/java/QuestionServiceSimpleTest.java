import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionType;
import ru.otus.spring.service.ParseServiceSimple;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionServiceSimpleTest {

    public static String MULTIPLE_QUESTION_TYPE;
    public static String FREE_QUESTION_TYPE;
    public static String QUESTION_TEXT;
    public static String ANSWERS;
    public static String DEFAULT_DELIMITER;
    public static List<String> ANSWERS_LIST;
    public static String MULTIPLE_TEST_QUESTION_ROW;
    public static String FREE_TEST_QUESTION_ROW;


    @BeforeAll
    public static void init() {
        MULTIPLE_QUESTION_TYPE = "multiple";
        FREE_QUESTION_TYPE = "free";
        QUESTION_TEXT = "How old are you?";
        ANSWERS = "<18;18-25;25-30;30-40;>40;";
        DEFAULT_DELIMITER = ";";
        ANSWERS_LIST = Arrays.asList(ANSWERS.split(DEFAULT_DELIMITER));
        MULTIPLE_TEST_QUESTION_ROW = MULTIPLE_QUESTION_TYPE + DEFAULT_DELIMITER + QUESTION_TEXT + DEFAULT_DELIMITER + ANSWERS;
        FREE_TEST_QUESTION_ROW = FREE_QUESTION_TYPE + DEFAULT_DELIMITER + QUESTION_TEXT + DEFAULT_DELIMITER + ANSWERS;
    }


    @Test
    public void isQuestionRowParseSuccessful() {
        ParseServiceSimple parseServiceSimple = new ParseServiceSimple();
        Question question = parseServiceSimple.parseQuestionRow(MULTIPLE_TEST_QUESTION_ROW.split(DEFAULT_DELIMITER));

        assertAll(() -> {
            assertEquals(question.getType(), QuestionType.MULTIPLE);
            assertEquals(question.getText(), QUESTION_TEXT);
            assertEquals(question.getAnswers(), ANSWERS_LIST);
        });
    }

    @Test
    public void ifQuestionTypeFreeQuestionAnswersMustBeEmpty() {
        ParseServiceSimple parseServiceSimple = new ParseServiceSimple();
        Question question = parseServiceSimple.parseQuestionRow(FREE_TEST_QUESTION_ROW.split(DEFAULT_DELIMITER));
        assertEquals(question.getAnswers(), Collections.emptyList());
    }

    @AfterAll
    public static void cleanUp() {
        MULTIPLE_QUESTION_TYPE = null;
        FREE_QUESTION_TYPE = null;
        QUESTION_TEXT = null;
        ANSWERS = null;
        DEFAULT_DELIMITER = null;
        ANSWERS_LIST = null;
        MULTIPLE_TEST_QUESTION_ROW = null;
        FREE_TEST_QUESTION_ROW = null;
    }
}
