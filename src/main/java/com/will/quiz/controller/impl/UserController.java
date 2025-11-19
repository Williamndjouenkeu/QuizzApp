package com.will.quiz.controller.impl;

import com.will.quiz.controller.IUserController;
import com.will.quiz.model.entity.UserEntity;
import com.will.quiz.model.request.QuizPlayApiRequest;
import com.will.quiz.model.request.QuizPlayApiRequestBody;
import com.will.quiz.model.response.QuizPlayApiResponse;
import com.will.quiz.model.response.UserApiResponse;
import com.will.quiz.repository.IQuizPlayRepository;
import com.will.quiz.repository.IUserRepository;
import com.will.quiz.validator.impl.QuizPlayValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController implements IUserController {

    private IUserRepository userRepository;

    private IQuizPlayRepository quizPlayRepository;

    private QuizPlayValidator quizPlayValidator;

    public UserController() {}

    public UserController(IUserRepository userRepository, IQuizPlayRepository quizPlayRepository,  QuizPlayValidator quizPlayValidator) {
        this.userRepository = userRepository;
        this.quizPlayRepository = quizPlayRepository;
        this.quizPlayValidator = quizPlayValidator;
    }

    @GetMapping("/users")
    public List<UserApiResponse> getUsers() {
        return userRepository.findAll().stream().map(UserEntity::toApiResponse).toList();
    }

    @PostMapping("/users/current/quizzes/{quizId}/plays")
    public ResponseEntity<Void> addQuizPlay(@PathVariable String quizId, @RequestBody QuizPlayApiRequestBody body, OAuth2AuthenticationToken token) {
        var request = QuizPlayApiRequest.builder().quizId(quizId).body(body).token(token).build();
        quizPlayRepository.save(quizPlayValidator.validate(request));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/current/quizzes/plays")
    public List<QuizPlayApiResponse> getQuizPlays(OAuth2AuthenticationToken token) {
        return userRepository.findByAccessToken(token).toQuizPlaysApiResponse();
    }
}
