package com.will.quiz.security;

import com.will.quiz.model.entity.UserEntity;
import com.will.quiz.repository.IUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Component
public class SuccessLoginHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String GITHUB_PROVIDER = "github";

    private TokenService tokenService;

    private IUserRepository userRepository;

    @Value("${app.oauth2.redirectUri}")
    private String redirectUri;

    public SuccessLoginHandler() {}

    public SuccessLoginHandler(TokenService tokenService, IUserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication instanceof OAuth2AuthenticationToken token) {
            var user = toUserEntity(token);
            userRepository.save(user);
            getRedirectStrategy().sendRedirect(request, response, getTargetUrl(user)) ;
        }
    }

    private String getTargetUrl(UserEntity user) {
        return UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam(ACCESS_TOKEN, tokenService.createAccessToken(user))
                .queryParam(REFRESH_TOKEN, user.getRefreshToken())
                .build().toString();
    }

    private UserEntity toUserEntity(OAuth2AuthenticationToken token) {
        var email =(String) Optional.ofNullable(token.getPrincipal().getAttribute("email")).orElse("NOT_FOUND");
        var provider = token.getAuthorizedClientRegistrationId();
        var res = userRepository.findByEmailAndProvider(email, provider).orElse(new UserEntity());
        res.setEmail(email);
        res.setProvider(provider);
        res.setName((String) Optional.ofNullable(token.getPrincipal().getAttribute("name")).orElse("NOT_FOUND"));
        res.setImage(token.getPrincipal().getAttribute(GITHUB_PROVIDER.equals(res.getProvider()) ? "avatar_url" : "picture"));
        res.setRefreshToken(tokenService.createRefreshToken());
        return res;
    }
}
