package com.example.restful_test.repository;

import com.example.restful_test.model.Lesson;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LessonRepository extends PagingAndSortingRepository<Lesson, Long> {
}
