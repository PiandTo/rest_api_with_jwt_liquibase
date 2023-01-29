package com.example.restful_test.exception.user;

public class UserNotSavedException extends RuntimeException{

    public UserNotSavedException(String message) {
        super(message);
    }
}
