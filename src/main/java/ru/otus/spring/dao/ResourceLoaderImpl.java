package ru.otus.spring.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.component.LocalePropsProducer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionsCsvParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class ResourceLoaderImpl implements ResourceLoader {

    private final QuestionsCsvParser questionsCsvParser;
    private final String questionsResourcePath;
    public String fileNameTemplate;

    public ResourceLoaderImpl(QuestionsCsvParser questionsCsvParser,
                              LocalePropsProducer localePropsProducer,
                              @Value("${questions.file.name_template}") String fileNameTemplate) {
        this.questionsCsvParser = questionsCsvParser;
        this.fileNameTemplate = fileNameTemplate;
        this.questionsResourcePath = String.format(fileNameTemplate, localePropsProducer.getLocale().getLanguage());
    }

    private InputStream getQuestionsResource() {
        return getClass().getResourceAsStream(this.questionsResourcePath);
    }

    @Override
    public List<Question> getQuestionsList() {
        List<Question> result = new ArrayList<>();
        try (InputStream questionsResource = this.getQuestionsResource()) {

            List<String> questionRows = this.questionsCsvParser.parseCsvResource(questionsResource);
            String defaultDelimiter = ";";
            questionRows.forEach(row -> {
                String[] fullQuestionRow = row.split(defaultDelimiter);
                Question question = questionsCsvParser.parseQuestionRow(fullQuestionRow);
                result.add(question);
            });
            return result;
        } catch (IOException e) {
            log.error("Не удалось закрыть ресурс \"questionsResource\"");
            log.error(ExceptionUtils.getStackTrace(e));
            return Collections.emptyList();
        }
    }
}
