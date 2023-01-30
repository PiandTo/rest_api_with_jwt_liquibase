package com.example.restful_test.service;

import com.example.restful_test.dto.LessonDto;
import com.example.restful_test.exception.course.CourseNotFoundException;
import com.example.restful_test.exception.course.CourseNotSavedException;
import com.example.restful_test.exception.lesson.LessonNotFoundException;
import com.example.restful_test.exception.user.UserNotFoundException;
import com.example.restful_test.model.Course;
import com.example.restful_test.model.Lesson;
import com.example.restful_test.model.User;
import com.example.restful_test.repository.CourseRepository;
import com.example.restful_test.repository.LessonRepository;
import com.example.restful_test.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;

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

    public Lesson addLessonToCourse(LessonDto lessonDto, long id) {
        Optional<Course> c = courseRepository.findById(id);
        Optional<User> teacher = userRepository.findById(lessonDto.getTeacherId());
        if (!teacher.isPresent()) {
            throw new CourseNotSavedException("No teacher with id "+ id + ". We cant add Lesson");
        }
        Lesson lesson = convertDtoToLesson(lessonDto);
        if (c.isPresent()) {
            try {
                lesson.setTeacher(teacher.get());
                teacher.get().addLessonToTeacher(lesson);
                c.get().addLessonToCourse(lesson);
                return lessonRepository.save(lesson);
            } catch (Exception e) {
                throw new CourseNotSavedException(e.getMessage());
            }
        }
        throw new CourseNotFoundException("No course with id " + id);
    }

    public Lesson findById(long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (lesson.isPresent()) {
            return lesson.get();
        }
        throw new LessonNotFoundException("No lesson with id " + id);
    }

    public Lesson updateLessonInCourse(Lesson oldLesson, LessonDto newLesson, long id) {
        Optional<User> teacher = userRepository.findById(newLesson.getTeacherId());
        if (!teacher.isPresent()) {
            throw new UserNotFoundException("No teacher with id " + newLesson.getTeacherId());
        }
        oldLesson.setDayOfWeek(newLesson.getDayOfWeek());
        oldLesson.setStartTime(newLesson.getStartTime());
        oldLesson.setEndTime(newLesson.getFinishTime());
        oldLesson.setTeacher(teacher.get());
        return lessonRepository.save(oldLesson);
    }

    private Lesson convertDtoToLesson(LessonDto l) {
        Lesson lesson = new Lesson();
        lesson.setEndTime(l.getFinishTime());
        lesson.setStartTime(l.getStartTime());
        lesson.setDayOfWeek(l.getDayOfWeek());
        return lesson;
    }
}
