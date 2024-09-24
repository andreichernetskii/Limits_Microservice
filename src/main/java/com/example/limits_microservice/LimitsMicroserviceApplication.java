package com.example.limits_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class LimitsMicroserviceApplication {

    public static void main( String[] args ) {
        SpringApplication.run( LimitsMicroserviceApplication.class, args );
    }

}
