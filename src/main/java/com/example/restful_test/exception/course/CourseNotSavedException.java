package com.example.restful_test.exception.course;

public class CourseNotSavedException extends RuntimeException{

    public CourseNotSavedException(String message) {
        super(message);
    }
}
