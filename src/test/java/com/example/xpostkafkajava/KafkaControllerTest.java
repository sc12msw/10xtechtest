package com.example.xpostkafkajava;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class KafkaControllerTest {
    KafkaProducer kafkaProducer = mock(KafkaProducer.class);
    Metrics metrics = mock(Metrics.class);
    private KafkaController controller = new KafkaController(kafkaProducer, metrics);

    @Test
    void sendValidMessage() {
        ResponseEntity<HttpStatus> expected = ResponseEntity.accepted().build();
        RequestMessage message = new RequestMessage();
        message.setBody("hello world");
        ResponseEntity<HttpStatus> result = controller.sendMessage("new", message);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource(value= {"new:''", "'':message", "'':''", "new:", ":"}, delimiter = ':')
    void sendMessage_ShouldReturnBadRequestForEmptyOrNullValues(String topic, String message){
        ResponseEntity<HttpStatus> expected = ResponseEntity.badRequest().build();
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setBody(message);
        ResponseEntity<HttpStatus> result = controller.sendMessage(topic, requestMessage);
        assertEquals(expected, result);
    }





}