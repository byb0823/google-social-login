package com.example.social_login.oauth.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
public class Provider {
    private String tokenUrl;
    private String userInfoUrl;
}
