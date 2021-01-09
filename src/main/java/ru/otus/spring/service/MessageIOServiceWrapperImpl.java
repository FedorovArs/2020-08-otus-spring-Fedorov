package ru.otus.spring.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageIOServiceWrapperImpl implements MessageIOServiceWrapper {

    private final IOService ioService;
    private final LocalizationService localizationService;

    @Override
    public void out(String message) {
        this.ioService.out(message);
    }

    @Override
    public void out(String format, Object... args) {
        this.ioService.out(format, args);
    }

    @Override
    public String readString() {
        return this.ioService.readString();
    }

    @Override
    public String getMessage(String msgKey) {
        return this.localizationService.getMessage(msgKey);
    }
}
