package com.will.quiz.model.entity;

import com.will.quiz.model.response.QuizPlayApiResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "T_QUIZ_PLAY")
@IdClass(QuizPlayEntity.PrimaryKey.class)
public class QuizPlayEntity {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private UserEntity user;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "QUIZ_ID", referencedColumnName = "ID")
    private QuizEntity quiz;

    @Column(name = "PLAYED_AT")
    private ZonedDateTime date;

    @Column(name = "CORRECT_QUESTIONS_NUMBER")
    private Integer correctQuestionsNumber;

    public QuizPlayApiResponse toApiResponse() {
        var res = new QuizPlayApiResponse();
        res.setQuizId(getQuiz().getId());
        res.setQuizTitle(getQuiz().getTitle());
        res.setPlayedAt(getDate().withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
        res.setQuestionsNumber(getQuiz().getQuestions().size());
        res.setCorrectQuestionsNumber(getCorrectQuestionsNumber());
        return res;
    }

    public static class PrimaryKey {
        private UserEntity user;
        private QuizEntity quiz;
    }
}
