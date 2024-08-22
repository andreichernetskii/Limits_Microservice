package com.example.limits_microservice.controller;

import com.example.limits_microservice.model.LimitDTO;
import com.example.limits_microservice.service.LimitServiceImpl;
import com.example.limits_microservice.entity.LimitEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling requests related to limits.
 */
@RestController
@RequestMapping( "/api/v1/limits" )
public class LimitController {
    private final LimitServiceImpl limitServiceImpl;

    public LimitController( LimitServiceImpl limitServiceImpl ) {
        this.limitServiceImpl = limitServiceImpl;
    }

    // Adds a new limit.
    @PostMapping( "/" )
    public void addNewLimit( @RequestBody LimitDTO limitDTO ) {
        limitServiceImpl.addLimit( limitDTO );
    }

    // Updates an existing limit.
    @PutMapping( "/{limitId}" )
    public void updateLimit( @PathVariable( "limitId" ) Long limitId, @RequestBody LimitDTO limitDTO ) {
        limitServiceImpl.updateLimit( limitId, limitDTO );
    }

    // Deletes an existing limit.
    @DeleteMapping( "/{limitId}" )
    public void deleteLimit( @PathVariable( "limitId" ) Long limitId ) {
        limitServiceImpl.deleteLimit( limitId );
    }

    // Gets a list of all limits.
    @GetMapping( "/" )
    public List<LimitEntity> getLimits() {
        return limitServiceImpl.getLimits();
    }

    // Gets a list of available limit types.
    @GetMapping( "/types" )
    public List<String> getLimitTypes() {
        return limitServiceImpl.getLimitTypes();
    }
}
