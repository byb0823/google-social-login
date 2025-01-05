package com.example.social_login.api.controller.dto.request;

import com.example.social_login.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OauthUserProfile {
    private String email;
    private String name;

    public User toUser() {
        return User.builder()
                .email(this.email)
                .name(this.name)
                .build();
    }
}
