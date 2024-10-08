package com.example.limits_microservice.service;

import com.example.limits_microservice.model.LimitDTO;

import java.util.List;

public interface LimitService {
    void deleteLimit( Long limitId );

    List<LimitDTO> getLimits();

    void addLimitAndSendToKafka( LimitDTO limitDTO );

    void updateLimit( LimitDTO limitDTO );
    List<String> getLimitTypes();
}
