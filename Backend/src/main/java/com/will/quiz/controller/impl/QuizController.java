package com.will.quiz.controller.impl;

import com.will.quiz.controller.IQuizController;
import com.will.quiz.model.entity.QuizEntity;
import com.will.quiz.model.request.QuizApiRequest;
import com.will.quiz.model.response.QuizApiResponse;
import com.will.quiz.repository.IQuizRepository;
import com.will.quiz.utils.JsonUtils;
import com.will.quiz.validator.impl.QuizValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
public class QuizController implements IQuizController {

    private final QuizValidator quizValidator;

    private final IQuizRepository quizRepository;

    public QuizController(QuizValidator quizValidator, IQuizRepository quizRepository) {
        this.quizValidator = quizValidator;
        this.quizRepository = quizRepository;
    }

    @GetMapping("/quizzes")
    public List<QuizApiResponse> getAll() {
        return quizRepository.findAll().stream().map(QuizEntity::toApiResponse).toList();
    }

    @GetMapping("/quizzes/{id}")
    public QuizApiResponse getById(@PathVariable String id) {
        var entity = quizValidator.validateId(id);
        return entity.toApiResponse();
    }

    @PostMapping("/quizzes")
    public QuizApiResponse create(@RequestBody QuizApiRequest request) {
        var entity = quizValidator.validate(request);
        quizRepository.save(entity);
        return entity.toApiResponse();
    }

    @DeleteMapping("/quizzes/{id}")
    public void delete(@PathVariable String id) {
        var entity = quizValidator.validateId(id);
        quizRepository.delete(entity);
    }

    @PutMapping("/quizzes/{id}")
    public void update(@PathVariable String id, @RequestBody QuizApiRequest request) {
        delete(id);
        create(request);
    }

    @GetMapping("/quizzes/export")
    public ResponseEntity<byte[]> exportQuizzes() {
        var quizzes = getAll();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quizzes.json")
                .body(JsonUtils.writeValueAsBytes(quizzes));
    }

    @PostMapping(value = "/quizzes/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void importQuizzes(@RequestPart MultipartFile file) {
        var request = JsonUtils.readValue(file, QuizApiRequest[].class);
        var quizzes = Arrays.stream(request).map(quizValidator::validate).toList();
        quizzes.forEach(quiz -> quizRepository.deleteByTitle(quiz.getTitle()));
        quizRepository.saveAll(quizzes);
    }

}
