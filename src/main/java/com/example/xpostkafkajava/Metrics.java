package com.example.xpostkafkajava;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Metrics {
    private MeterRegistry meterRegistry;

    @Autowired
    public Metrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void incrementStatusCode(HttpStatus status) {
        meterRegistry.counter("kafka.request.status", "status", status.name()).increment();
    }

    public void incrementMessageSent(Boolean successful) {
        String tag = Boolean.TRUE.equals(successful) ? "success" : "failed";
        meterRegistry.counter("kafka.message.sent", "messageSent", tag);
    }
}
