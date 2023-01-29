package com.example.restful_test.controller;

import com.example.restful_test.model.Lesson;
import com.example.restful_test.service.LessonService;
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
    public ResponseEntity<?> getLessonsByCourse (@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "1") int pageSize,
                                                 @RequestParam(name= "sortBy", defaultValue = "id", required = false) String sortBy,
                                                 @PathVariable(name = "course_id") long id) {
        Iterable<Lesson> lessons = lessonService.getAllByCourse(pageNo, pageSize, sortBy, id);
        return ResponseEntity.ok().body(lessons);
    }
}
