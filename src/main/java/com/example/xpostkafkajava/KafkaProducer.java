package com.example.xpostkafkajava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, RequestMessage message){
        kafkaTemplate.send(topic, message.getBody());
    }
}
