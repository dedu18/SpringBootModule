package com.dedu.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component
public class KafkaConsumer {

    /**
     * 这里指定分组信息
     * @param content
     */
    @KafkaListener(id = "test1", topics = "group-test-topic", groupId = "test1")
    public void processMessage(String content) {
        System.out.println("收到groupId:test1消息=>" + content);
    }

    /**
     * 多个分组可订阅同一个Topic，每个分组都会接受到消息
     * @param content
     */
    @KafkaListener(id = "test2", topics = "group-test-topic", groupId = "test2")
    public void processMessage1(String content) {
        System.out.println("收到groupId:test2消息=>" + content);
    }
}