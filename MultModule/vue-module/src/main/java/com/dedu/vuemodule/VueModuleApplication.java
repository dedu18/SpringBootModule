package com.dedu.vuemodule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.dedu.vuemodule.dao")
@EnableSwagger2
public class VueModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueModuleApplication.class, args);
    }

}
