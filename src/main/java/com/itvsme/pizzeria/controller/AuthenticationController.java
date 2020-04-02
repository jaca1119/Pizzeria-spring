package com.itvsme.pizzeria.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController
{
    @PostMapping("/authenticate")
    public String authenticate()
    {
        return "Authenticated";
    }
}
