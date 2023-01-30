package com.example.restful_test.dto;

import com.example.restful_test.model.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalTime;

@Data
public class LessonDto {
	@JsonFormat(pattern = "HH:mm")
	private LocalTime startTime;
	@JsonFormat(pattern = "HH:mm")
	private LocalTime finishTime;
	@Enumerated(EnumType.STRING)
	private DayOfWeek dayOfWeek;
	private long teacherId;
}
