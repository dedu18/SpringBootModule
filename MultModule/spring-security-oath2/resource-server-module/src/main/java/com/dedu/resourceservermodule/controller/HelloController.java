package com.dedu.resourceservermodule.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello, I am /";
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public String getHello(String access_token) {
        return "Hello, I am /hello";
    }
}
