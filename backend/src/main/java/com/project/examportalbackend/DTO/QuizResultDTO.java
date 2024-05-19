package com.project.examportalbackend.DTO;

import com.project.examportalbackend.models.Quiz;
import lombok.Data;

@Data
public class QuizResultDTO {
    private Long quizResId;
    private String name;
    private float totalObtainedMarks;
    private String attemptDatetime;
    private Quiz quiz;
}
