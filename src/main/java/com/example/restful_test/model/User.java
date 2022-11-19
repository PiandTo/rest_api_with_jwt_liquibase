package com.example.restful_test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "students")
    private List<Course> studentsCourses = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "teachers")
    private List<Course> teachersCourses = new ArrayList<>();
}
