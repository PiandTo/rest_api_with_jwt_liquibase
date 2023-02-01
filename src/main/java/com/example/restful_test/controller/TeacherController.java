package com.example.restful_test.controller;

import com.example.restful_test.json.UserRequest;
import com.example.restful_test.json.UserResponse;
import com.example.restful_test.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@Tag(name = "Teacher Controller", description = "REST controller provide services to manages teacher")
public class TeacherController {
	private final TeacherService teacherService;

	@Autowired
	public TeacherController(TeacherService userService) {
		this.teacherService = userService;
	}

	@GetMapping("/{course_id}/teachers")
	@Operation(summary = "Формирование списка всех Учителей курса")
	public ResponseEntity<?> getTeachersFromCourse(@PathVariable("course_id") long id) {
		HashSet<UserResponse> courses = teacherService.findAllByRoleAndId(id);
		return ResponseEntity.ok().body(courses);
	}

	@PostMapping("/{course_id}/teachers")
	@Operation(summary = "Добавление нового учителя на Курс")
	public ResponseEntity<UserResponse> addTeacherToCourse(
			@PathVariable("course_id") long id,
			@RequestBody UserRequest user
	) {
		UserResponse addedTeacher = teacherService.addTeacherToCourse(user, id);
		return ResponseEntity.ok().body(addedTeacher);
	}

	@DeleteMapping("/{course_id}/teachers/{teacher_id}")
	@Operation(summary = "Удаление Учителя из Курса")
	public ResponseEntity<?> deleteTeacherFromCourse(
			@PathVariable("course_id") long course_id,
			@PathVariable("teacher_id") long teacher_id
	) {
		teacherService.deleteTeacherFromCourse(course_id, teacher_id);
		return ResponseEntity.ok().build();
	}
}
