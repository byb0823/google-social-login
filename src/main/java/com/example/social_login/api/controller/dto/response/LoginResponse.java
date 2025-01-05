package com.example.social_login.api.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private long id;
    private String email;
    private String name;
    private String accessToken;

    @Builder
    private LoginResponse(long id, String email, String name, String accessToken) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.accessToken = accessToken;
    }
}
