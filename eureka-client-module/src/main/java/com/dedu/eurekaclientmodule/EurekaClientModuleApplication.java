package com.dedu.eurekaclientmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaClientModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientModuleApplication.class, args);
    }

}
