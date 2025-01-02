package com.example.social_login.oauth.inmemory;

import com.example.social_login.oauth.properties.OauthProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthAdapter {

    public static Map<String, OauthProvider> convertToOauthProviderMap(OauthProperties properties) {
        Map<String, OauthProvider> oauthProviderMap = new HashMap<>();

        properties.getClient()
                .forEach(
                        (key, value) ->
                                oauthProviderMap.put(key, new OauthProvider(value, properties.getProvider().get(key)))
                );

        return oauthProviderMap;
    }
}
