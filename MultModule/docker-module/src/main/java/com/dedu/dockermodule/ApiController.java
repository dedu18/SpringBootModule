package com.dedu.dockermodule;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/docker")
    public String hello(@RequestParam("name") String name) {
        System.out.println("Docker Hello:" + name);
        return "Docker Hello:" + name;
    }
}
