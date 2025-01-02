package com.example.social_login.oauth.inmemory;

import com.example.social_login.oauth.properties.Client;
import com.example.social_login.oauth.properties.Provider;

public class OauthProvider {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String tokenUrl;
    private final String userInfoUrl;

    public OauthProvider(Client client, Provider provider) {
        this(client.getClientId(),
                client.getClientSecret(),
                client.getRedirectUrl(),
                provider.getTokenUrl(),
                provider.getUserInfoUrl());
    }

    private OauthProvider(String clientId, String clientSecret, String redirectUrl, String tokenUrl, String userInfoUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.tokenUrl = tokenUrl;
        this.userInfoUrl = userInfoUrl;
    }
}
