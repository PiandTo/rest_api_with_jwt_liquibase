package com.example.restful_test.exception.course;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String e) {
        super(e);
    }
}
