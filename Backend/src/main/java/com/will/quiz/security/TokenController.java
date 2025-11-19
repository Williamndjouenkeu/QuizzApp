package com.will.quiz.security;

import com.will.quiz.exception.RefreshTokenException;
import com.will.quiz.model.entity.UserEntity;
import com.will.quiz.model.response.RefreshTokenApiResponse;
import com.will.quiz.repository.IUserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.will.quiz.security.SuccessLoginHandler.REFRESH_TOKEN;

@RestController
public class TokenController {

    private TokenService tokenService;

    private IUserRepository userRepository;

    public TokenController() {}

    public TokenController(TokenService tokenService, IUserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @GetMapping("/token/refresh")
    public RefreshTokenApiResponse getRefreshToken(@RequestParam(REFRESH_TOKEN) String refreshToken) {
        var user = userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RefreshTokenException("Refresh token not found"));
        if(user.isRefreshTokenExpired()){
            throw new RefreshTokenException("Refresh token expired");
        }
        var accessToken = tokenService.createAccessToken(user);
        var newRefreshToken = tokenService.createRefreshToken();
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);
        return new RefreshTokenApiResponse(accessToken, newRefreshToken);
    }
}
