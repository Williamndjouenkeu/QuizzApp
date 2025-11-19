package com.will.quiz.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class QuizApiResponse {

    private UUID id;
    private String title;
    private String description;
    private List<String> tags;
    private String thumbnail;
    private List<QuizQuestionApiResponse> questions;
}
