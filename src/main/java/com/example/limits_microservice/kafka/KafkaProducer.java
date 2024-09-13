package com.example.limits_microservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // after a new limit creation his limit type will be sent to Kafka for consumers
    public void sendLimitTypeOfNewLimit( String limitType ) {
        for ( int i = 0; i < 2; i++ ) {
            kafkaTemplate.send( "limit_types", "message with limit type " + i, limitType );
        }
    }

    public void sendLimitAmount( String limitAmount ) {
        kafkaTemplate.send( "limit_amount", limitAmount );
    }
}
