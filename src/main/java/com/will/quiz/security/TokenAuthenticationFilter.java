package com.will.quiz.security;

import com.will.quiz.utils.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    public TokenAuthenticationFilter() {}

    public TokenAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        getToken(request).ifPresent(token -> SecurityContextHolder.getContext().setAuthentication(token));
        chain.doFilter(request, response);
    }

    private Optional<OAuth2AuthenticationToken> getToken(HttpServletRequest request) {
        try {
            var token = request.getHeader("Authorization");
            if (StringUtils.isBlank(token)) {
                return Optional.empty();
            }
            var claims = tokenService.getClaims(token.replace("Bearer ", ""));
            var user = tokenService.getUser(claims);
            var provider = tokenService.getProvider(claims);
            return Optional.of(new OAuth2AuthenticationToken(user, user.getAuthorities(), provider));
        } catch (Exception e) {
            log.error("Failed to parse access token", e);
            return Optional.empty();
        }
    }
}
