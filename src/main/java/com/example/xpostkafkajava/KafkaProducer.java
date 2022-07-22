package com.example.xpostkafkajava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class KafkaProducer {
    Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    public boolean sendMessage(String topic, RequestMessage message) {
        try {
            kafkaTemplate.send(topic, message.body);
            String logMessage = MessageFormat.format("Message {1} was sent to topic {0}", topic, message.body);
            logger.info(logMessage);
            return true;
        } catch (Exception error) {
            String logMessage = MessageFormat.format("An error as occurred when trying to send a message: {0}",
                    error.getMessage());
            logger.error(logMessage);
            return false;
        }
    }
}
