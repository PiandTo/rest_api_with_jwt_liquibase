package com.example.restful_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotSavedException extends RuntimeException{

    public UserNotSavedException(String message) {
        super(message);
    }
}
