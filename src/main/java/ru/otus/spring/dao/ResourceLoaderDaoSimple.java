package ru.otus.spring.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceLoaderDaoSimple implements ResourceLoaderDao {

    private String resourcePath;

    @Override
    public InputStream getResource() {
        return getClass().getResourceAsStream(this.resourcePath);
    }
}
