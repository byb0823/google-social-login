package com.example.social_login.config.oauth;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "oauth2.client")
public class Client {

    private String clientId;
    private String clientSecret;
    private String redirectUrl;
}
