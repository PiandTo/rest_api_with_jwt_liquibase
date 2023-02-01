package com.example.restful_test.controller;

import com.example.restful_test.json.UserRequest;
import com.example.restful_test.json.UserResponse;
import com.example.restful_test.model.User;
import com.example.restful_test.service.StudentService;
import com.example.restful_test.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/courses")
public class StudentController {
	private final StudentService studentService;

	@Autowired
	public StudentController(StudentService userService) {
		this.studentService = userService;
	}

	@GetMapping("/{course_id}/students")
	@Operation(summary = "Формирование списка всех Студентов курса")
	public ResponseEntity<?> getStudentsFromCourse(@PathVariable("course_id") long id) {
		HashSet<UserResponse> courses = studentService.findAllByRoleAndId(id);
		return ResponseEntity.ok().body(courses);
	}

	@PostMapping("/{course_id}/students")
	@Operation(summary = "Добавление нового студента на Курс")
	public ResponseEntity<UserResponse> addStudentToCourse(
			@PathVariable("course_id") long id,
			@RequestBody UserRequest user
			) {
			UserResponse addedStudent = studentService.addStudentToCourse(user, id);
			return ResponseEntity.ok().body(addedStudent);
	}

	@DeleteMapping("/{course_id}/students/{student_id}")
	@Operation(summary = "Удаление Студента из Курса")
	public ResponseEntity<?> deleteStudentFromCourse(
			@PathVariable("course_id") long course_id,
			@PathVariable("student_id") long student_id
	) {
		studentService.deleteStudentFromCourse(course_id, student_id);
		return ResponseEntity.ok().build();
	}
}
