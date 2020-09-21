package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.SpringBootAppProps;

import java.io.InputStream;
import java.util.Locale;

@Component
public class ResourceLoaderImpl implements ResourceLoader {

    private final String questionsResourcePath;
    private final SpringBootAppProps springBootAppProps;

    public ResourceLoaderImpl(@Value("${questions.file.en_path}") String enQuestionsResourcePath,
                              @Value("${questions.file.ru_path}") String ruQuestionsResourcePath,
                              SpringBootAppProps springBootAppProps) {

        this.springBootAppProps = springBootAppProps;

        if (this.springBootAppProps.getLocale().equals(Locale.US)) {
            this.questionsResourcePath = enQuestionsResourcePath;
        } else {
            this.questionsResourcePath = ruQuestionsResourcePath;
        }
    }

    @Override
    public InputStream getQuestionsResource() {
        return getClass().getResourceAsStream(this.questionsResourcePath);
    }

    @Override
    public Locale getLocale() {
        return this.springBootAppProps.getLocale();
    }
}
