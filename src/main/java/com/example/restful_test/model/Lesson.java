package com.example.restful_test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.PERSIST })
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @JsonIgnore
    @ManyToMany(mappedBy = "lessons", cascade = { CascadeType.DETACH, CascadeType.PERSIST })
    private List<Course> courses = new ArrayList<>();
}
