package com.will.quiz.model.entity;

import com.will.quiz.model.response.QuizApiResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "T_QUIZ")
public class QuizEntity {

    public static final String TAGS_SEPARATOR = ",";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    private String tags;

    private String thumbnail;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "quiz")
    private List<QuizQuestionEntity> questions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "quiz")
    private List<QuizPlayEntity> plays;

    public QuizApiResponse toApiResponse() {
        var res = new QuizApiResponse();
        res.setId(getId());
        res.setTitle(getTitle());
        res.setDescription(getDescription());
        res.setTags(Arrays.stream(getTags().split(TAGS_SEPARATOR)).toList());
        res.setThumbnail(getThumbnail());
        res.setQuestions(getQuestions().stream().map(QuizQuestionEntity::toApiResponse).toList());
        return res;
    }

}
