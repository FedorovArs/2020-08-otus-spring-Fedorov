package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleIOService implements IOService {
    private final PrintStream out;
    private final Scanner sc;

    public ConsoleIOService(@Value("#{ T(java.lang.System).in}") InputStream in,
                            @Value("#{ T(java.lang.System).out}") PrintStream out) {
        this.out = out;
        this.sc = new Scanner(in);
    }

    @Override
    public void out(String message) {
        out.println(message);
    }

    @Override
    public String readString() {
        return sc.nextLine().trim();
    }
}
