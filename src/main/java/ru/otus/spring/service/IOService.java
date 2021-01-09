package ru.otus.spring.service;

public interface IOService {
    void out(String message);

    void out(String format, Object... args);

    String readString();
}
