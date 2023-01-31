package com.example.restful_test.controller;

import com.example.restful_test.json.UserResponse;
import com.example.restful_test.model.User;
import com.example.restful_test.service.StudentService;
import com.example.restful_test.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
