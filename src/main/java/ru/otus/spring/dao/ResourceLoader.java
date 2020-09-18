package ru.otus.spring.dao;

import java.io.InputStream;
import java.util.Locale;

public interface ResourceLoader {

    InputStream getQuestionsResource();

    Locale getLocale();
}
