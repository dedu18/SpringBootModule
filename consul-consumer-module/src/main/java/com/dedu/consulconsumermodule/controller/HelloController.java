package com.dedu.consulconsumermodule.controller;


import com.dedu.consulconsumermodule.Feign.ConsulClientFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ConsulClientFeignClient consulClientFeignClient;

    @RequestMapping("/helloconsumer")
    public String  helloconsumer() {
        return consulClientFeignClient.hello();
    }
}
