package com.example.restful_test.service;

import com.example.restful_test.exception.course.CourseNotFoundException;
import com.example.restful_test.model.Course;
import com.example.restful_test.model.Lesson;
import com.example.restful_test.repository.CourseRepository;
import com.example.restful_test.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    private LessonRepository lessonRepository;
    private CourseRepository courseRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    public Iterable<Lesson> getAllByCourse(Integer pageNo, Integer pageSize, String sortBy, long id) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Optional<Course> c = courseRepository.findById(id);
        if (c.isPresent()) {
            return c.get().getLessons();
        } else {
            throw new CourseNotFoundException("No Course with id " + id);
        }
    }
}
