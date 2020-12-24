package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Question;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class QuestionShellImpl implements QuestionShell {

    private final DataProducerComponent questionService;
    private final TestServiceSimple testServiceSimple;

    @ShellMethod(value = "Start test", key = {"start"})
    public String runTest() {
        List<Question> questions = questionService.getQuestionsList();
        testServiceSimple.runTest(questions);
        return "Тест завершен";
    }
}
