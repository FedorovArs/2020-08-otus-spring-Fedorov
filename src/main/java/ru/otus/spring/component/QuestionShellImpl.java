package ru.otus.spring.component;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.dao.ResourceLoader;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.TestService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class QuestionShellImpl implements QuestionShell {

    private final ResourceLoader resourceLoader;
    private final TestService testService;

    @ShellMethod(value = "Start test", key = {"start"})
    public String runTest() {
        List<Question> questions = resourceLoader.getQuestionsList();
        testService.runTest(questions);
        return "Тест завершен";
    }
}
