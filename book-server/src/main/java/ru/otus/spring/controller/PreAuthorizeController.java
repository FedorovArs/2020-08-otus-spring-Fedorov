package ru.otus.spring.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.service.PreAuthorizeServiceImpl;

@RestController
@AllArgsConstructor
public class PreAuthorizeController {

    private final PreAuthorizeServiceImpl preAuthorizeService;

    @GetMapping("/pre_auth_user")
    public String onlyUserAccessGranted() {
        return preAuthorizeService.onlyUser();
    }

    @GetMapping("/pre_auth_manager")
    public String onlyManagerAccessGranted() {
        return preAuthorizeService.onlyManager();
    }

    @GetMapping("/pre_auth_admin")
    public String onlyAdminAccessGranted() {
        return preAuthorizeService.onlyAdmin();
    }

    @GetMapping("/pre_auth_manager_or_admin")
    public String onlyManagerOrAdminAccessGranted() {
        return preAuthorizeService.onlyManagerOrAdmin();
    }
}
