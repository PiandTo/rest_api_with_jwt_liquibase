package com.example.restful_test.repository;

import com.example.restful_test.model.Role;
import com.example.restful_test.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.HashSet;

public interface TeacherRepository extends PagingAndSortingRepository<User, Long> {
	public HashSet<User> findByRoleAndListTeacherCourses_Id(Role role, long id);
}
