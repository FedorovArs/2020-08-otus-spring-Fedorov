package ru.otus.spring.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocalePropsProducerImpl implements LocalePropsProducer {

    private final Locale locale;

    public LocalePropsProducerImpl(@Value("${application.locale}") Locale locale) {
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }
}
