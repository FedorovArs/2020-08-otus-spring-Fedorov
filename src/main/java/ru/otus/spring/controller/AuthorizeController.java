package ru.otus.spring.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizeController {

    @GetMapping("/auth_manager")
    public String onlyManagerAccessGrantedPage(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("name", userDetails.getUsername());
        model.addAttribute("roles", userDetails.getAuthorities());
        return "access_granted";
    }

    @GetMapping("/auth_admin")
    public String onlyAdminAccessGrantedPage(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("name", userDetails.getUsername());
        model.addAttribute("roles", userDetails.getAuthorities());
        return "access_granted";
    }

    @GetMapping("/auth_user")
    public String onlyUserAccessGrantedPage(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("name", userDetails.getUsername());
        model.addAttribute("roles", userDetails.getAuthorities());
        return "access_granted";
    }

    @GetMapping("/auth_admin_or_manager")
    public String onlyAdminOrManagerAccessGrantedPage(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("name", userDetails.getUsername());
        model.addAttribute("roles", userDetails.getAuthorities());
        return "access_granted";
    }
}
