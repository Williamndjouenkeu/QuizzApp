package com.will.quiz.validator.impl;

import com.will.quiz.model.entity.QuizEntity;
import com.will.quiz.model.entity.QuizQuestionEntity;
import com.will.quiz.model.request.QuizQuestionApiRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.will.quiz.model.entity.QuizQuestionEntity.OPTIONS_SEPARATOR;

@Service
public class QuizQuestionValidator {

    private CommonValidator commonValidator;

    public QuizQuestionValidator(CommonValidator commonValidator) {
        this.commonValidator = commonValidator;
    }

    public QuizQuestionEntity validate(QuizQuestionApiRequest request, QuizEntity quiz) {
        var res = new QuizQuestionEntity();
        res.setQuiz(quiz);
        res.setQuestion(commonValidator.validateMandatoryString(request.getQuestion(),"question"));
        res.setAnswerIndex(commonValidator.validateMandatoryInteger(request.getAnswerindex(), "answerIndex"));
        res.setOptions(commonValidator.validateMandatoryString(String.join(OPTIONS_SEPARATOR, request.getOptions()), "options"));
        return res;
    }
}
