package com.example.limits_microservice.exception_handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.NOT_FOUND )
public class NotFoundException extends RuntimeException {
    public NotFoundException( String message ) {
        super( message );
    }
}
