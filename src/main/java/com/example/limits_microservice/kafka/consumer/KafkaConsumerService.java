package com.example.limits_microservice.kafka.consumer;

import com.example.limits_microservice.security.PublicKeyVault;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    public static final Logger logger = LoggerFactory.getLogger( KafkaConsumerService.class );
    private final PublicKeyVault publicKeyVault;

    @KafkaListener( topics = "public_key_distribution",
            groupId = "${public-key-consumer}",
            containerFactory = "publicKeyListenerContainerFactory" )
    public void listen( String publicKey ) {
        publicKeyVault.convertStringToPublicKey( publicKey );
        logger.info( "Public key: " + publicKey );
    }
}
