package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.ResourceLoaderImpl;
import ru.otus.spring.domain.Question;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataProducerComponent implements DataProducer {

    private final ResourceLoaderImpl resourceLoaderDao;
    private final ParseServiceImpl parseServiceImpl;
    private final MessageSource messageSource;

    public DataProducerComponent(ResourceLoaderImpl resourceLoaderDao, ParseServiceImpl parseServiceImpl,
                                 MessageSource messageSource) {
        this.resourceLoaderDao = resourceLoaderDao;
        this.parseServiceImpl = parseServiceImpl;
        this.messageSource = messageSource;
    }

    public List<Question> getQuestionsList() {
        List<Question> result = new ArrayList<>();
        InputStream questionsResource = resourceLoaderDao.getQuestionsResource();

        List<String> questionRows = parseServiceImpl.parseCsvResource(questionsResource);

        questionRows.forEach(row -> {
            String[] fullQuestionRow = row.split(DEFAULT_DELIMITER);
            Question question = parseServiceImpl.parseQuestionRow(fullQuestionRow);
            result.add(question);
        });

        return result;
    }

    @Override
    public String getLocalizeMsg(String msgKey) {
        return messageSource.getMessage(msgKey, null, resourceLoaderDao.getLocale());
    }
}
