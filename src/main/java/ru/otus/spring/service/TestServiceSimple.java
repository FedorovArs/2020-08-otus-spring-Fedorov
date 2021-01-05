package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.Collections;
import java.util.List;

@Service
public class TestServiceSimple implements TestService {

    private final int minSuccessfulValue;
    private final IOService iOService;
    private final LocalizationService localizeProducer;

    public TestServiceSimple(@Value("${questions.min.value}") int minSuccessfulValue,
                             IOService iOService,
                             LocalizationService localizeProducer
    ) {
        this.minSuccessfulValue = minSuccessfulValue;
        this.iOService = iOService;
        this.localizeProducer = localizeProducer;
    }

    @Override
    public int getMinSuccessfulValue() {
        return this.minSuccessfulValue;
    }

    @Override
    public void runTest(List<Question> questions) {
        iOService.out(localizeProducer.getMessage("ask.name"));
        String name = iOService.readString();

        iOService.out(localizeProducer.getMessage("ask.surname"));
        String surname = iOService.readString();

        //mix questions
        Collections.shuffle(questions);


        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            iOService.out(String.format("%s. " + localizeProducer.getMessage("question.word") + ": %s", i + 1, question.getText()));
            iOService.out(String.format(localizeProducer.getMessage("possible.answers") + ": %s", question.getAnswers()));

            question.setUserAnswer(iOService.readString());
            iOService.out("=========================================================================");
        }

        int passedTest = getTestPercentsResults(questions);
        iOService.out(String.format(localizeProducer.getMessage("result.msg"), surname, name, passedTest));
        iOService.out(passedTest < minSuccessfulValue ? localizeProducer.getMessage("test.failed.msg") :
                localizeProducer.getMessage("test.passed.msg"));
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
