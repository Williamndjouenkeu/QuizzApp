package com.will.quiz.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizQuestionApiRequest {

    private String question;

    private List<String> options;

    @Schema(implementation = Integer.class)
    private String answerindex;
}
