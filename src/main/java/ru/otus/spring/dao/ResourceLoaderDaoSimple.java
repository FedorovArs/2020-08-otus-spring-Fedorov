package ru.otus.spring.dao;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Data
@Component("resourceLoaderDao")
@PropertySource("classpath:application.properties")
public class ResourceLoaderDaoSimple implements ResourceLoaderDao {

    private String resourcePath;

    public ResourceLoaderDaoSimple(@Value("${questions.file.path}") String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public InputStream getResource() {
        return getClass().getResourceAsStream(this.resourcePath);
    }
}
