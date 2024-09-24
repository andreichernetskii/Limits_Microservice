package com.example.limits_microservice.kafka.producer;

import com.example.limits_microservice.model.LimitDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger( KafkaProducerService.class );
    private final KafkaTemplate<String, LimitDTO> limitDTOKafkaTemplate;

    @Value( value = "${kafka.producer.group1}" )
    private String limitDTOTopicName;

    public void sendLimitDTO( LimitDTO limitDTO ) {
        CompletableFuture<SendResult<String, LimitDTO>> future =
                this.limitDTOKafkaTemplate.send( limitDTOTopicName, limitDTO );

        future.whenComplete( ( result, throwable ) -> {
            if ( throwable != null ) {
                logger.error( "Unable to send limit data", throwable );
            } else {
                logger.info( "Limit data successfully sent. " + result.getRecordMetadata().offset() );
            }
        } );
    }

}
