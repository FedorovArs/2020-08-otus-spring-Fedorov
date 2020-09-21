package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.Collections;
import java.util.List;

import static ru.otus.spring.Utils.scan;

@PropertySource("classpath:application.properties")
@Service(value = "testService")
public class TestServiceSimple implements TestService {

    private int minSuccessfulValue;

    public TestServiceSimple(@Value("${questions.min.value}") int minSuccessfulValue) {
        this.minSuccessfulValue = minSuccessfulValue;
    }

    @Override
    public int getMinSuccessfulValue() {
        return this.minSuccessfulValue;
    }

    @Override
    public void runTest(List<Question> questions) {
        String name = askUserAndGetAnswer("What is your name?");
        String suname = askUserAndGetAnswer("What is your suname?");

        //mix questions
        Collections.shuffle(questions);


        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            System.out.printf("%s. Question: %s%n", i + 1, question.getText());
            System.out.printf("Possible answers: %s%n", question.getAnswers());

            question.setUserAnswer(scan.nextLine().trim());
            System.out.println("==================================================");
        }

        int passedTest = testResults(questions);
        System.out.println(String.format("Dear %s %s You answered %s%% of the questions correctly", suname, name, passedTest));
        System.out.println(String.format("%s. Bye!", passedTest < minSuccessfulValue ? "Test failed" : "Test passed"));
    }

    private int testResults(List<Question> questions) {
        int result = 0;

        for (Question question : questions) {
            if (question.getUserAnswer().equalsIgnoreCase(question.getCorrectAnswer())) {
                result++;
            }
        }

        return (int) (((double) result) / questions.size() * 100);


    }

    private String askUserAndGetAnswer(String answer) {
        System.out.println(answer);
        return scan.nextLine().trim();
    }
}
