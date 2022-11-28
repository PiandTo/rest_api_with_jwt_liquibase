package com.example.restful_test.exception;

public class UserNotSavedException extends RuntimeException{

    public UserNotSavedException(String message) {
        super(message);
    }
}
