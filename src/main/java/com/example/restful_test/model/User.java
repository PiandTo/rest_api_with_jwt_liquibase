package com.example.restful_test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString(onlyExplicitlyIncluded=true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "user", schema = "restful")
public class User extends BaseEntity{
    private String firstName;
    private String lastName;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String login;
    private String password;

    @ManyToMany (cascade = { CascadeType.PERSIST, CascadeType.DETACH}, mappedBy = "teachers")
    private Set<Course> listTeacherCourses = new HashSet<>();

    @ManyToMany (cascade = { CascadeType.PERSIST, CascadeType.DETACH}, mappedBy = "students")
    private Set<Course> listStudentCourses = new HashSet<>();

    //    @ToString.Exclude
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST}, mappedBy = "teacher")
    private Set<Lesson> lessons = new HashSet<>();

    public void addCourse(Course course) {
        this.listTeacherCourses.add(course);
        course.getTeachers().add(this);
    }

    public void removeCourse(Course course) {
        this.listTeacherCourses.remove(course);
        course.getTeachers().remove(this);
    }

    public void addStudent(Course course) {
        this.listStudentCourses.add(course);
        course.getStudents().add(this);
    }

    public void removeStudent(Course course) {
        this.listStudentCourses.remove(course);
        course.getStudents().remove(this);
    }

    public void addLessonToTeacher(Lesson lesson) {
        lessons.add(lesson);
        lesson.setTeacher(this);
    }


}
