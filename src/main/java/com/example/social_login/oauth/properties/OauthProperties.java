package com.example.social_login.oauth.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "oauth2")
public class OauthProperties {
    private final Map<String, Client> client;
    private final Map<String, Provider> provider;
}
