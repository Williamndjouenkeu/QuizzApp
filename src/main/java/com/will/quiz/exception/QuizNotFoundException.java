package com.will.quiz.exception;

public class QuizNotFoundException extends NotFoundException  {
    public QuizNotFoundException(String id) {
        super("Quiz avec l'id " + id + " non trouvé");
    }
}
