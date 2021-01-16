package ru.otus.spring.service;

public interface PreAuthorizeService {

    String onlyUser();

    String onlyAdmin();

    String onlyManager();

    String onlyManagerOrAdmin();
}
