package com.dedu.zuulmodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZuulClientController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello, I am Zuul !";
    }

    @RequestMapping("/tokenerror")
    public String error() {
        return "Token Error Found! Please Try Later!";
    }
}
