package com.example.social_login.oauth;

import com.example.social_login.oauth.inmemory.InMemoryProviderRepository;
import com.example.social_login.oauth.inmemory.OauthAdapter;
import com.example.social_login.oauth.properties.OauthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OauthProperties.class)
@RequiredArgsConstructor
public class OauthConfig {

    private final OauthProperties properties;

    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository() {
        return new InMemoryProviderRepository(OauthAdapter.convertToOauthProviderMap(properties));
    }
}
