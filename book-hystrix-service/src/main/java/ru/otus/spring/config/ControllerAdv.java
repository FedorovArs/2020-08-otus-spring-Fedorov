package ru.otus.spring.config;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class ControllerAdv {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("msg", e.getMessage());
        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> accessError() {
        return ResponseEntity.of(Optional.of("Вам доступ запрещен, обратитесь к аминистратору!"));
    }
}
