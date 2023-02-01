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
import com.example.restful_test.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class TeacherService {
	private final TeacherRepository teacherRepository;
	private final CourseRepository courseRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public TeacherService(
			TeacherRepository teacherRepository,
			PasswordEncoder passwordEncoder,
			CourseRepository courseRepository) {
		this.teacherRepository = teacherRepository;
		this.passwordEncoder = passwordEncoder;
		this.courseRepository = courseRepository;
	}


	public HashSet<UserResponse> findAllByRoleAndId(long id) {
		HashSet<User> teachers = teacherRepository.findByRoleAndListTeacherCourses_Id(Role.TEACHER, id);
		if (teachers.isEmpty()) {
			throw new UserNotFoundException("No teacher with course id " + id);
		}
		HashSet<UserResponse> u = new HashSet<>();
		for (User us : teachers) {
			u.add(convert(us));
		}
		return u;
	}

	public UserResponse addTeacherToCourse(UserRequest user, long id) {
		Optional<Course> course = courseRepository.findById(id);
		User newTeacher = new User();
		if (course.isPresent()) {
			try {
				newTeacher.setFirstName(user.getFirstName());
				newTeacher.setLastName(user.getFirstName());
				newTeacher.setRole(Role.TEACHER);
				newTeacher.setLogin(user.getLogin());
				newTeacher.setPassword(passwordEncoder.encode(user.getPassword()));
				newTeacher.addTeacher(course.get());
				return convert(teacherRepository.save(newTeacher));
			} catch (Exception e) {
				throw new UserNotSavedException("No saved Teacher with name " + user.getFirstName());
			}
		}
		throw new CourseNotFoundException("No course with such id " + id);
	}

	public void deleteTeacherFromCourse(long course_id, long teacher_id) {
		Optional<User> user = teacherRepository.findById(teacher_id);
		Optional<Course> course = courseRepository.findById(course_id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("Teacher not found with id " + teacher_id);
		}
		if (course.isPresent()) {
			user.get().removeTeacher(course.get());
			teacherRepository.delete(user.get());
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
