package com.example.social_login.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OauthProperties {

    private final Map<String, Client> client;
    private final Map<String, Provider> provider;

}
