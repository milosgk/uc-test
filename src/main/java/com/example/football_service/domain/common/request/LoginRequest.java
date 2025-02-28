package com.example.football_service.domain.common.request;

import lombok.Builder;

@Builder
public record LoginRequest(String email, String password) {
    public static LoginRequest from(String email, String password) {
        return LoginRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}
