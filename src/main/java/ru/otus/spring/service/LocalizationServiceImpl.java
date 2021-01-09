package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.component.LocalePropsProducer;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final LocalePropsProducer localePropsProducer;
    private final MessageSource messageSource;

    @Override
    public String getMessage(String msgKey) {
        return messageSource.getMessage(msgKey, null, localePropsProducer.getLocale());
    }
}
