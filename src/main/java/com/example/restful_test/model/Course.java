package com.example.restful_test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.*;

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

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH })
    @JoinTable (name = "teachers_courses", schema = "restful",
            joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id", referencedColumnName = "id")})
    private Set<User> teachers = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH })
    @JoinTable (name = "students_courses", schema = "restful",
            joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")})
    private Set<User> students = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH })
    @JoinTable (name = "lessons_courses", schema = "restful",
        joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "lesson_id", referencedColumnName = "id")})
    private Set<Lesson> lessons = new HashSet<>();
}
