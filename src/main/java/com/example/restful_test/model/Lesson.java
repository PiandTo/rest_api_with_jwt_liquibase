package com.example.restful_test.model;

import lombok.*;

import javax.persistence.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString(onlyExplicitlyIncluded=true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name="lesson", schema = "restful")
public class Lesson extends BaseEntity{
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek;

    @ManyToOne
    private User teacher;

    @ManyToMany(mappedBy = "lessons")
    private List<Course> courses = new ArrayList<>();
}
