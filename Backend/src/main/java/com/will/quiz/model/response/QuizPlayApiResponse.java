package com.will.quiz.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class QuizPlayApiResponse {

    private UUID quizId;
    private String quizTitle;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime playedAt;
    private Integer questionsNumber;
    private Integer correctQuestionsNumber;
}
