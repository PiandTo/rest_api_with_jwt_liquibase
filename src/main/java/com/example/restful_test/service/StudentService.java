package com.example.restful_test.service;

import com.example.restful_test.exception.user.UserNotFoundException;
import com.example.restful_test.json.UserResponse;
import com.example.restful_test.model.Role;
import com.example.restful_test.model.User;
import com.example.restful_test.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class StudentService {
	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}


	public HashSet<UserResponse> findAllByRoleAndId(long id) {
		HashSet<User> students = studentRepository.findByRoleAndListStudentCourses_Id(Role.STUDENT, id);
		if (students.isEmpty()) {
			throw new UserNotFoundException("No students with course id " + id);
		}
		HashSet<UserResponse> u = new HashSet<>();
		for (User us : students) {
			u.add(convert(us));
		}
		return u;
	}

	private UserResponse convert (User user) {
		UserResponse u = new UserResponse();
		u.setId(user.getId());
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		return u;
	}
}
