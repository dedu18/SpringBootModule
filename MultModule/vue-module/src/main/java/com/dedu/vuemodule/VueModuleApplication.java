package com.dedu.vuemodule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dedu.vuemodule.dao")
public class VueModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueModuleApplication.class, args);
    }

}
