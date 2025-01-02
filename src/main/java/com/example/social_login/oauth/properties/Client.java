package com.example.social_login.oauth.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
public class Client {
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
}
