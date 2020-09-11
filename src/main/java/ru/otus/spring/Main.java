package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionType;
import ru.otus.spring.service.QuestionServiceSimple;

import java.util.List;

public class Main {

    public static final String QUESTION = "Question";
    public static final String ANSWERS = "Answers";
    public static final String DELIMITER = ": ";

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionServiceSimple service = context.getBean(QuestionServiceSimple.class);
        List<Question> questions = service.getQuestionsList();

        questions.forEach(s -> {
            if (s.getType() == QuestionType.FREE) {
                System.out.println(QUESTION + DELIMITER + s.getText());
            } else {
                System.out.println(QUESTION + DELIMITER + s.getText());
                System.out.println(ANSWERS + DELIMITER + s.getAnswers());
            }
        });
    }
}
