package com.example.limits_microservice.exception_handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.UNAUTHORIZED )
public class UserNotAuthenticatedException extends RuntimeException {
    public UserNotAuthenticatedException( String message ) {
        super( message );
    }
}
