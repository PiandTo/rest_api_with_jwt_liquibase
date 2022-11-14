package com.example.restful_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUUID = 5071646428281007896L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
