package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.ResourceLoader;

@Component
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final ResourceLoader resourceLoader;
    private final MessageSource messageSource;

    @Override
    public String getMessage(String msgKey) {
        return messageSource.getMessage(msgKey, null, resourceLoader.getLocale());
    }
}
