package com.dedu.eurekaclientmodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(String code) {
        return code + " <br /> Hello, I Am Eureka Client！";
    }
}
