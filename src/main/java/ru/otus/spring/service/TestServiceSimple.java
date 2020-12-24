package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.Collections;
import java.util.List;

import static ru.otus.spring.Utils.scan;

@Service
public class TestServiceSimple implements TestService {

    private final int minSuccessfulValue;
    private final DataProducerComponent dataProducerComponent;
    private final OpenedConsoleIOService openedConsoleIOService;

    public TestServiceSimple(@Value("${questions.min.value}") int minSuccessfulValue,
                             DataProducerComponent dataProducerComponent,
                             OpenedConsoleIOService openedConsoleIOService
    ) {
        this.minSuccessfulValue = minSuccessfulValue;
        this.dataProducerComponent = dataProducerComponent;
        this.openedConsoleIOService = openedConsoleIOService;
    }

    @Override
    public int getMinSuccessfulValue() {
        return this.minSuccessfulValue;
    }

    @Override
    public void runTest(List<Question> questions) {
        openedConsoleIOService.out(dataProducerComponent.getLocalizeMsg("ask.name"));
        String name = openedConsoleIOService.readString();

        openedConsoleIOService.out(dataProducerComponent.getLocalizeMsg("ask.surname"));
        String surname = openedConsoleIOService.readString();

        //mix questions
        Collections.shuffle(questions);


        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            openedConsoleIOService.out(String.format("%s. " + dataProducerComponent.getLocalizeMsg("question.word") + ": %s", i + 1, question.getText()));
            openedConsoleIOService.out(String.format(dataProducerComponent.getLocalizeMsg("possible.answers") + ": %s", question.getAnswers()));

            question.setUserAnswer(openedConsoleIOService.readString());
            openedConsoleIOService.out("=========================================================================");
        }

        int passedTest = getTestPercentsResults(questions);
        openedConsoleIOService.out(String.format(dataProducerComponent.getLocalizeMsg("result.msg"), surname, name, passedTest));
        openedConsoleIOService.out(passedTest < minSuccessfulValue ? dataProducerComponent.getLocalizeMsg("test.failed.msg") :
                dataProducerComponent.getLocalizeMsg("test.passed.msg"));
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

    private String askUserAndGetAnswer(String answer) {
        System.out.println(answer);
        return scan.nextLine().trim();
    }
}
