package com.example.restful_test.repository;

import com.example.restful_test.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> { }
