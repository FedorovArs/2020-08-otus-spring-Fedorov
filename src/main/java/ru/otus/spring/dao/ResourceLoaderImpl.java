package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.ParseService;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ru.otus.spring.Utils.fileNameTemplate;

@Component
public class ResourceLoaderImpl implements ResourceLoader {

    private final ParseService parseService;

    private String questionsResourcePath;

    @Value("${application.locale}")
    private Locale locale;

    @PostConstruct
    private void init() {
        this.questionsResourcePath = String.format(fileNameTemplate, this.locale.getLanguage());
    }

    public ResourceLoaderImpl(ParseService parseService) {
        this.parseService = parseService;
    }

    private InputStream getQuestionsResource() {
        return getClass().getResourceAsStream(this.questionsResourcePath);
    }

    @Override
    public List<Question> getQuestionsList() {
        List<Question> result = new ArrayList<>();
        InputStream questionsResource = this.getQuestionsResource();

        List<String> questionRows = this.parseService.parseCsvResource(questionsResource);
        String defaultDelimiter = ";";
        questionRows.forEach(row -> {
            String[] fullQuestionRow = row.split(defaultDelimiter);
            Question question = parseService.parseQuestionRow(fullQuestionRow);
            result.add(question);
        });

        return result;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }
}
