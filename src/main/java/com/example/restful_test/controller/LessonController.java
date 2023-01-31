package com.example.restful_test.controller;

import com.example.restful_test.dto.LessonDto;
import com.example.restful_test.model.Course;
import com.example.restful_test.model.Lesson;
import com.example.restful_test.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/courses")
@RestController
public class LessonController {

    private LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/{course_id}/lessons")
    @Operation(summary = "Получение списка всех уроков в курсе")
    public ResponseEntity<?> getLessonsByCourse (@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "1") int pageSize,
                                                 @RequestParam(name= "sortBy", defaultValue = "id", required = false) String sortBy,
                                                 @PathVariable(name = "course_id") long id) {
        Iterable<Lesson> lessons = lessonService.getAllByCourse(pageNo, pageSize, sortBy, id);
        return ResponseEntity.ok().body(lessons);
    }

    @PostMapping("/{course_id}/lessons")
    @Operation(summary = "Добавление нового урока в курс")
    public ResponseEntity<Lesson> addLessonToCourse (
            @RequestBody LessonDto l, @PathVariable(name = "course_id") long id) {
        Lesson lesson = lessonService.addLessonToCourse(l, id);
        return ResponseEntity.ok(lesson);
    }

    @PutMapping("/{course_id}/lessons/{lesson_id}")
    @Operation(summary = "Обновление информации по Уроку")
    public ResponseEntity<Lesson> updateLessonInCourse(
        @PathVariable(name = "course_id") long course_id,
        @PathVariable(name = "lesson_id") long lesson_id,
        @RequestBody LessonDto lesson
    ) {
        Lesson oldLesson = lessonService.findById(lesson_id);
        Lesson newLesson = lessonService.updateLessonInCourse(oldLesson, lesson, course_id);
        return ResponseEntity.ok(newLesson);
    }

    @DeleteMapping("/{course_id}/lessons/{lesson_id}")
    @Operation(summary = "Удаление урока по id из Курса")
    public ResponseEntity<?> deleteLessonFromCourse(
            @PathVariable("course_id") long course_id,
            @PathVariable("lesson_id") long lesson_id
                                       ) {
            lessonService.deleteLessonFromCourse(course_id, lesson_id);
            return ResponseEntity.ok().build();
    }
}
