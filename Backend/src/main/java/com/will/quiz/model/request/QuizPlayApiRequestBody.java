package com.will.quiz.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizPlayApiRequestBody {

    @Schema(implementation = Integer.class)
    private String correctQuestionsNumber;
}
