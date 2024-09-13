package com.example.limits_microservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    // topic for Transactions and FinAnalyzer microservices
    @Bean
    public NewTopic addLimitTypesTopic() {
        return new NewTopic( "limit_types", 2, ( short ) 1 );
    }

    // this is for FinAnalyzer microservice
    @Bean
    public NewTopic addLimitAmountTopic() {
        return new NewTopic( "limit_amount", 1, ( short ) 1 );
    }
}
