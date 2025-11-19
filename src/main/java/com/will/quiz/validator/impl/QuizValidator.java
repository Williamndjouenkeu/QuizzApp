package com.will.quiz.validator.impl;

import com.will.quiz.model.entity.QuizEntity;
import com.will.quiz.model.request.QuizApiRequest;
import com.will.quiz.repository.IQuizRepository;
import org.springframework.stereotype.Component;

import static com.will.quiz.model.entity.QuizEntity.TAGS_SEPARATOR;

@Component
public class QuizValidator {

    private IQuizRepository quizRepository;

    private CommonValidator commonValidator;

    private QuizQuestionValidator questionValidator;

    public QuizValidator(){}

    public QuizValidator(IQuizRepository quizRepository, CommonValidator commonValidator, QuizQuestionValidator questionValidator) {
        this.quizRepository = quizRepository;
        this.commonValidator = commonValidator;
        this.questionValidator = questionValidator;
    }

    public QuizEntity validateId(String id) {
        return quizRepository.getById(commonValidator.validateMandatoryUUID(id));
    }

    public QuizEntity validate(QuizApiRequest request) {
        var res = new QuizEntity();
        res.setTitle(commonValidator.validateMandatoryString(request.getTitle(),"title"));
        res.setDescription(commonValidator.validateMandatoryString(request.getDescription(),"description"));
        res.setThumbnail(commonValidator.validateMandatoryString(request.getThumbnail(),"thumbnail"));
        res.setTags(commonValidator.validateMandatoryString(String.join(TAGS_SEPARATOR, request.getTags()), "tags"));
        res.setQuestions(request.getQuestions().stream().map(question -> questionValidator.validate(question, res)).toList());
        return res;
    }
}
