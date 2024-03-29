package com.example.restful_test.service;

import com.example.restful_test.exception.course.CourseNotFoundException;
import com.example.restful_test.exception.user.UserNotFoundException;
import com.example.restful_test.exception.user.UserNotSavedException;
import com.example.restful_test.json.UserRequest;
import com.example.restful_test.json.UserResponse;
import com.example.restful_test.model.Course;
import com.example.restful_test.model.Role;
import com.example.restful_test.model.User;
import com.example.restful_test.repository.CourseRepository;
import com.example.restful_test.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class StudentService {
	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public StudentService(
			StudentRepository studentRepository,
			PasswordEncoder passwordEncoder,
			CourseRepository courseRepository) {
		this.studentRepository = studentRepository;
		this.passwordEncoder = passwordEncoder;
		this.courseRepository = courseRepository;
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

	public UserResponse addStudentToCourse(UserRequest user, long id) {
		Optional<Course> course = courseRepository.findById(id);
		User newStudent = new User();
		if (course.isPresent()) {
			try {
				newStudent.setFirstName(user.getFirstName());
				newStudent.setLastName(user.getFirstName());
				newStudent.setRole(Role.STUDENT);
				newStudent.setLogin(user.getLogin());
				newStudent.setPassword(passwordEncoder.encode(user.getPassword()));
				newStudent.addStudent(course.get());
				return convert(studentRepository.save(newStudent));
			} catch (Exception e) {
				throw new UserNotSavedException("No saved Student with name " + user.getFirstName());
			}
		}
		throw new CourseNotFoundException("No course with such id " + id);
	}

	public void deleteStudentFromCourse(long course_id, long student_id) {
		Optional<User> user = studentRepository.findById(student_id);
		Optional<Course> course = courseRepository.findById(course_id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("Student not found with id " + student_id);
		}
		if (course.isPresent()) {
			user.get().removeStudent(course.get());
			studentRepository.delete(user.get());
			return ;
		}
		throw new CourseNotFoundException("Course not found with id " + course_id);
	}

	private UserResponse convert (User user) {
		UserResponse u = new UserResponse();
		u.setId(user.getId());
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		return u;
	}
}
