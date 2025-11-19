package com.will.quiz.controller;

import com.will.quiz.model.request.QuizPlayApiRequestBody;
import com.will.quiz.model.response.ErrorApiResponse;
import com.will.quiz.model.response.QuizPlayApiResponse;
import com.will.quiz.model.response.UserApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.List;

@Tag(name = "User")
public interface IUserController {
    @Operation(summary = "Get All Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorApiResponse.class))),
    })
    List<UserApiResponse> getUsers();

    @Operation(summary = "Add Quiz Play to Current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorApiResponse.class))),
    })
    ResponseEntity<Void> addQuizPlay(String id, QuizPlayApiRequestBody request, OAuth2AuthenticationToken authentication);

    @Operation(summary = "Get Current User's Quiz Plays")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorApiResponse.class))),
    })
    List<QuizPlayApiResponse> getQuizPlays(OAuth2AuthenticationToken authentication);
}
