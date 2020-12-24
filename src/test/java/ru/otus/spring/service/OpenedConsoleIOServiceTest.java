package ru.otus.spring.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Тест OpenedConsoleIOService")
public class OpenedConsoleIOServiceTest {

    private static final String TEXT_1 = "Текст для печати №1";
    private static final String TEXT_2 = "Текст для печати №2";

    private ByteArrayOutputStream byteStream;
    private IOService ioService;

    @BeforeEach
    void setUp() {
        byteStream = new ByteArrayOutputStream();
        ioService = new OpenedConsoleIOService(System.in, new PrintStream(byteStream));
    }

    @DisplayName("должно печатать \"" + TEXT_1 + "\"")
    @SneakyThrows
    @Test
    void shouldPrintOnlyFirstCreedLine() {
        ioService.out(TEXT_1);
        TimeUnit.SECONDS.sleep(1);
        assertThat(byteStream.toString()).isEqualTo(TEXT_1 + "\n");
    }

    @DisplayName("должно печатать \"" + TEXT_2 + "\"")
    @SneakyThrows
    @Test
    void shouldPrintOnlySecondCreedLine() {
        ioService.out(TEXT_2);
        assertThat(byteStream.toString()).isEqualTo(TEXT_2 + "\n");
    }
}
