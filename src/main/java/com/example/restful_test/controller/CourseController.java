package com.example.restful_test.controller;

import com.example.restful_test.model.Course;
import com.example.restful_test.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
@Tag(
        name ="Курсы (Course)",
        description = "Все методы для работы с курсами (CourseController)"
)
public class CourseController {

    private final CourseService courseService;

    @PostMapping()
    @Operation(summary="Добавить новый курс (addCourse)")
    public @ResponseBody
    ResponseEntity<Course> addCourse(@RequestBody @Valid Course request) {
        Course course = courseService.addCourse(request);
        return ResponseEntity.ok(Objects.requireNonNull(course));
    }

    @GetMapping()
    @Operation(summary = "Извлечь все курсы (getAllCourses)")
    public ResponseEntity<?> getAllCourses(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy
    ) {
        Iterable<Course> courses = courseService.findAllCourses(pageNo, pageSize, sortBy);
        log.info("Get method - GetAllCourses function");
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/{course_id}")
    @Operation(summary = "Извлечение курса по id")
    public ResponseEntity<?> getCourse(@PathVariable("course_id") long id) {
        Course course = courseService.findById(id);
        return ResponseEntity.ok().body(course);
    }

    @PutMapping("/{course_id}")
    @Operation(summary = "Обновление информациии о Курсе по ID")
    public ResponseEntity<Course> updateCourse(@PathVariable("course_id") long id, @RequestBody Course course) {
        Course courseDTO = courseService.update(id, course);
        return ResponseEntity.ok().body(courseDTO);

    }

    @DeleteMapping("/{course_id}")
    public void delete (@PathVariable("course_id") long id) {
        courseService.delete(id);
    }
}
