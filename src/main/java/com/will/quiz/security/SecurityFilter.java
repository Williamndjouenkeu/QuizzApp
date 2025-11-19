package com.will.quiz.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
public class SecurityFilter {

    public static final String ADMIN_ROLE = "ADMIN";

    private SuccessLoginHandler successLoginHandler;

    private TokenAuthenticationFilter tokenAuthenticationFilter;

    public SecurityFilter(SuccessLoginHandler successLoginHandler, TokenAuthenticationFilter tokenAuthenticationFilter) {
        this.successLoginHandler = successLoginHandler;
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, "/quizzes", "/quizzes/*", "/token/refresh/", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users", "/quizzes/export").hasAnyAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, "/quizzes/*").hasAnyAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, "/quizzes/*").hasAnyAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, "/quizzes/*").hasAnyAuthority(ADMIN_ROLE)
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login.successHandler(successLoginHandler))
                .addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
