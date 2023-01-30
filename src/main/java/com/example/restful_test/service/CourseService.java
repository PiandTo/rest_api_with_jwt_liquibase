package com.example.restful_test.service;

import com.example.restful_test.exception.course.CourseNotFoundException;
import com.example.restful_test.exception.course.CourseNotSavedException;
import com.example.restful_test.model.Course;
import com.example.restful_test.model.User;
import com.example.restful_test.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        
    }

    public Course addCourse(Course course) {
        try {
            courseRepository.save(course);
        } catch (Exception e){
            throw new CourseNotSavedException("Cant save course " + course.toString());
        }
        return (course);
    }

    public Iterable<Course> findAllCourses(Integer pageNo, Integer pageSize, String sortBy) {
        int size = 0;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Iterable<Course> courses = courseRepository.findAll(pageable);
        for (Course c: courses) {
            size++;
        }
        if (size == 0) {throw new CourseNotFoundException("Not found course at all");}
        return courses;
    }

    public Course findById (long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new CourseNotFoundException("No course with id " + id);
        }
    }

    public Course update(long id, Course course) {
        try {
            Optional<Course> courseDTO = courseRepository.findById(id);
            if (courseDTO.isPresent()) {
                return courseRepository.save(course);
            }
        } catch (DataAccessException e) {
            throw new CourseNotSavedException("We can't save Course " + course.toString());
        } catch (IllegalArgumentException e) {
            throw new CourseNotFoundException("We can't found Course with id " + course.getId());
        }
        return null;
    }

    public void delete(long id) {
        try {
            Optional<Course> course = courseRepository.findById(id);
            if (course.isPresent()) {
                for (User c : course.get().getTeachers()) {
                    course.get().getTeachers().remove(c);
                }
                courseRepository.delete(course.get());
            }
        } catch (DataAccessException e) {
            throw new CourseNotSavedException("We can't delete Course");
        } catch (IllegalArgumentException e) {
            throw new CourseNotFoundException("We can't found Course with id " + id);
        }
    }
}
