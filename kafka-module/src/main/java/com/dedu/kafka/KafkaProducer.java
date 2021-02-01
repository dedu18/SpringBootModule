package com.dedu.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Kafka消息生产类
 */
@Component
public class KafkaProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * kafka topic 名称
     */
    private String topic = "group-test-topic";

    /**
     *
     * @param msg
     */
    public void sendUserMessage(String msg) {
        kafkaTemplate.send(topic, msg);
        System.out.println("生产消息到Topic，msg：" + msg);
    }
}