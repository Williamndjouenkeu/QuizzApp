package com.will.quiz.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizApiRequest {

    private String title;
    private String description;
    private List<String> tags;
    private String thumbnail;
    private List<QuizQuestionApiRequest> questions;
}
