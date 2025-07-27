package com.example.coffeeshop.controllers;

import com.example.coffeeshop.security.InMemoryUserService;
import com.example.coffeeshop.security.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final InMemoryUserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(InMemoryUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userService.saveUser(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}