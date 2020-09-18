package ru.otus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

// Такой варинат получения Local сделан в рамках обучения,
// получить значение через @Value конечно легче
@ConfigurationProperties(prefix = "application")
public class SpringBootAppProps {
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    // Без сеттера значение не вставляется.
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
