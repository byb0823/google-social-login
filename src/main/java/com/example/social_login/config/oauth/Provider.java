package com.example.social_login.config.oauth;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "oauth2.provider")
public class Provider {

    private String tokenUrl;
    private String userInfoUrl;
}
