package com.example.xpostkafkajava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class KafkaController {
    private final KafkaProducer producer;
    private final Metrics metrics;
    @Autowired
    public KafkaController(KafkaProducer producer, Metrics metrics){
        this.producer = producer;
        this.metrics = metrics;
    }
    @PostMapping("/{topic}")
    public ResponseEntity<HttpStatus> sendMessage(@PathVariable String topic, @RequestBody RequestMessage message) {
        if (message.body == null || topic == null || topic.isEmpty() || message.body.isEmpty()){
            metrics.incrementStatusCode(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().build();
        }
        Boolean messageSent = producer.sendMessage(topic, message);
        metrics.incrementMessageSent(messageSent);
        if (Boolean.FALSE.equals(messageSent)){
            metrics.incrementStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().build();
        }
        metrics.incrementStatusCode(HttpStatus.ACCEPTED);
        return ResponseEntity.accepted().build();
    }
}
