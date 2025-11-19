package com.will.quiz.repository;

import com.will.quiz.model.entity.QuizPlayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizPlayRepository extends JpaRepository<QuizPlayEntity, QuizPlayEntity.PrimaryKey> {

}
