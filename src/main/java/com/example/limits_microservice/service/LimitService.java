package com.example.limits_microservice.service;

import com.example.limits_microservice.model.LimitDTO;

import java.util.List;

public interface LimitService {
    void deleteLimit( Long limitId );

    List<LimitDTO> getLimits();

    void addLimit( LimitDTO limitDTO );

    void updateLimit( Long limitId, LimitDTO limitDTO );
    List<String> getLimitTypes();
}
