package com.dedu.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private KafkaProducer kafkaProducer;

    @RequestMapping("send")
    public String createMsg(@RequestParam String id) {
        kafkaProducer.sendUserMessage(id);
        return "success";
    }

}
