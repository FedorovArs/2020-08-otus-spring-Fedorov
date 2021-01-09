package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.Collections;
import java.util.List;

@Service
public class TestServiceSimple implements TestService {

    private final int minSuccessfulValue;
    private final MessageIOServiceWrapper messageIOServiceWrapper;

    public TestServiceSimple(@Value("${questions.min.value}") int minSuccessfulValue,
                             MessageIOServiceWrapper messageIOServiceWrapper
    ) {
        this.minSuccessfulValue = minSuccessfulValue;
        this.messageIOServiceWrapper = messageIOServiceWrapper;
    }

    @Override
    public int getMinSuccessfulValue() {
        return this.minSuccessfulValue;
    }

    @Override
    public void runTest(List<Question> questions) {
        messageIOServiceWrapper.out(messageIOServiceWrapper.getMessage("ask.name"));
        String name = messageIOServiceWrapper.readString();

        messageIOServiceWrapper.out(messageIOServiceWrapper.getMessage("ask.surname"));
        String surname = messageIOServiceWrapper.readString();

        //mix questions
        Collections.shuffle(questions);


        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            messageIOServiceWrapper.out("%s. " + messageIOServiceWrapper.getMessage("question.word") + ": %s", i + 1, question.getText());
            messageIOServiceWrapper.out(messageIOServiceWrapper.getMessage("possible.answers") + ": %s %s", question.getAnswers(), "\n");

            question.setUserAnswer(messageIOServiceWrapper.readString());
            messageIOServiceWrapper.out("=========================================================================");
        }

        int passedTest = getTestPercentsResults(questions);
        messageIOServiceWrapper.out(messageIOServiceWrapper.getMessage("result.msg"), surname, name, passedTest);
        messageIOServiceWrapper.out(passedTest < minSuccessfulValue ? messageIOServiceWrapper.getMessage("test.failed.msg") :
                messageIOServiceWrapper.getMessage("test.passed.msg"));
    }

    private int getTestPercentsResults(List<Question> questions) {
        int result = 0;

        for (Question question : questions) {
            if (question.getUserAnswer().equalsIgnoreCase(question.getCorrectAnswer())) {
                result++;
            }
        }

        return (int) (((double) result) / questions.size() * 100);
    }
}
