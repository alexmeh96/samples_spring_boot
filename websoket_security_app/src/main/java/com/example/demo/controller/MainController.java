package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {
    @GetMapping
    public String mainPage() {
        return "Main page";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "Public page";
    }

    @GetMapping("/private")
    public String privatePage(Principal principal) {
        String username = principal.getName();
        return "Private page: user - " + username;
    }
}