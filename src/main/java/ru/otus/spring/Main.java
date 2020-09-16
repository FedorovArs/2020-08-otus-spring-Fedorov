package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionServiceSimple;
import ru.otus.spring.service.TestServiceSimple;

import java.util.List;

@ComponentScan
@Configuration
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuestionServiceSimple service = context.getBean(QuestionServiceSimple.class);
        List<Question> questions = service.getQuestionsList();

        TestServiceSimple testService = (TestServiceSimple) context.getBean("testService");
        testService.runTest(questions);

    }
}
