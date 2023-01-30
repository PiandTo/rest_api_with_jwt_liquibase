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

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH }, fetch = FetchType.LAZY)
    @JoinTable (name = "teachers_courses", schema = "restful",
            joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id", referencedColumnName = "id")})
    private Set<User> teachers = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH }, fetch = FetchType.LAZY)
    @JoinTable (name = "students_courses", schema = "restful",
            joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")})
    private Set<User> students = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH }, fetch = FetchType.LAZY)
    @JoinTable (name = "lessons_courses", schema = "restful",
        joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "lesson_id", referencedColumnName = "id")})
    private Set<Lesson> lessons = new HashSet<>();

    public void addLessonToCourse(Lesson l) {
        this.getLessons().add(l);
        l.getCourses().add(this);
    }
}
