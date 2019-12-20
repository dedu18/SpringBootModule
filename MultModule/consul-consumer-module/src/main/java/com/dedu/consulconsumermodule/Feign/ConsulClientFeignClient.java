package com.dedu.consulconsumermodule.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("consul-client-module")
@Component
public interface ConsulClientFeignClient {
    @RequestMapping("/hello")
    String hello();
}
