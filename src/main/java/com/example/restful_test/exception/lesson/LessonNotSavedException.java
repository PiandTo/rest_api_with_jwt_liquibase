package com.example.restful_test.exception.lesson;

public class LessonNotSavedException extends RuntimeException{

    public LessonNotSavedException(String message) {
        super(message);
    }
}
