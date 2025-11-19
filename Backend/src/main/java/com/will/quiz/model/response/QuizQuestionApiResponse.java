package com.will.quiz.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizQuestionApiResponse {

    private String question;
    private List<String> options;
    private Integer answerIndex;
}
