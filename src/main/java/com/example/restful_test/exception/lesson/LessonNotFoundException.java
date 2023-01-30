package com.example.restful_test.exception.lesson;

public class LessonNotFoundException extends RuntimeException{
    public LessonNotFoundException(String e) {
        super(e);
    }
}
