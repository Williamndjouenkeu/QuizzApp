package com.will.quiz.validator.impl;

import com.will.quiz.model.entity.QuizPlayEntity;
import com.will.quiz.model.request.QuizPlayApiRequest;
import com.will.quiz.repository.IUserRepository;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class QuizPlayValidator {

    private CommonValidator commonValidator;

    private QuizValidator quizValidator;

    private IUserRepository userRepository;

    public QuizPlayValidator(){}

    public QuizPlayValidator(CommonValidator commonValidator, QuizValidator quizValidator, IUserRepository userRepository) {
        this.commonValidator = commonValidator;
        this.quizValidator = quizValidator;
        this.userRepository = userRepository;
    }

    public QuizPlayEntity validate(QuizPlayApiRequest request) {
        var res = new QuizPlayEntity();
        res.setUser(userRepository.findByAccessToken(request.getToken()));
        res.setQuiz(quizValidator.validateId(request.getQuizId()));
        res.setDate(ZonedDateTime.now());
        res.setCorrectQuestionsNumber(commonValidator.validateMandatoryInteger(request.getCorrectQuestionsNumber(), "correctQuestionNumber"));
        return res;
    }
}
