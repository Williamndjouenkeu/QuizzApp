package com.will.quiz.model.entity;

import com.will.quiz.model.response.QuizQuestionApiResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "T_QUIZ_QUESTION")
public class QuizQuestionEntity {

    public static final String OPTIONS_SEPARATOR = ",";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String question;

    private String options;

    @Column(name = "ANSWER_INDEX")
    private Integer answerIndex;

    @ManyToOne
    @JoinColumn(name = "QUIZ_ID", referencedColumnName = "ID")
    private QuizEntity quiz;

    public QuizQuestionApiResponse toApiResponse() {
        var res = new QuizQuestionApiResponse();
        res.setQuestion(getQuestion());
        res.setOptions(Arrays.stream(getOptions().split(OPTIONS_SEPARATOR)).toList());
        res.setAnswerIndex(getAnswerIndex());
        return res;
    }
}
