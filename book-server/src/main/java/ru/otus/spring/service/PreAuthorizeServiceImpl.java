package ru.otus.spring.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PreAuthorizeServiceImpl implements PreAuthorizeService {

    private final String ACCESS_IS_ALLOWED = "Доступ резрешен пользователю с ролью(ями): ";

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public String onlyUser() {
        return ACCESS_IS_ALLOWED + "USER";
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String onlyAdmin() {
        return ACCESS_IS_ALLOWED + "ADMIN";
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String onlyManager() {
        return ACCESS_IS_ALLOWED + "MANAGER";
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public String onlyManagerOrAdmin() {
        return ACCESS_IS_ALLOWED + "MANAGER or ADMIN";
    }
}
