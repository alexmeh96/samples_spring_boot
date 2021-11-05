package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
