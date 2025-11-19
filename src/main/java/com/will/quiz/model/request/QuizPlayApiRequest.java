package com.will.quiz.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@Getter
@Setter
@Builder
public class QuizPlayApiRequest {

    private String quizId;

    private OAuth2AuthenticationToken token;

    private QuizPlayApiRequestBody body;

    public String getCorrectQuestionsNumber() {
        return body.getCorrectQuestionsNumber();
    };
}
