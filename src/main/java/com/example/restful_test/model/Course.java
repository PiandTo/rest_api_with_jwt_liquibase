package com.example.restful_test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity{
    private Date startDate;
    private Date endDate;
    private String name;
    private String description;
}
