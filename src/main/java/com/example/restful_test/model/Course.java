package com.example.restful_test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString(onlyExplicitlyIncluded=true)
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course", schema = "restful")
public class Course extends BaseEntity{
    private Date startDate;
    private Date endDate;
    private String name;
    private String description;

    @ManyToMany()
    @JoinTable (name = "course_teacher",
            joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id", referencedColumnName = "id")})
    private List<User> teachers = new ArrayList<>();

    @ManyToMany()
    @JoinTable (name = "course_student",
            joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")})
    private List<User> students = new ArrayList<>();

    @ManyToMany()
    @JoinTable (name = "course_lesson",
        joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "lesson_id", referencedColumnName = "id")})
    private List<Lesson> lessons = new ArrayList<>();
}
