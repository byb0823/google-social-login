package com.example.social_login.domain;

import com.example.social_login.api.controller.dto.request.OauthUserProfile;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "Users")
@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    @Builder
    private User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public User update(OauthUserProfile profile) {
        this.email = profile.getEmail();
        this.name = profile.getName();
        return this;
    }

}
