package com.example.social_login.api.service;

import com.example.social_login.api.controller.dto.request.OauthUserProfile;
import com.example.social_login.api.controller.dto.response.LoginResponse;
import com.example.social_login.api.controller.dto.response.OauthTokenResponse;
import com.example.social_login.domain.User;
import com.example.social_login.domain.UserRepository;
import com.example.social_login.oauth.inmemory.InMemoryProviderRepository;
import com.example.social_login.oauth.inmemory.OauthProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class OauthService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public LoginResponse login(String providerName, String code) {
        OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

        //access token 발급
        OauthTokenResponse response = getAccessToken(code, provider);

        //회원 정보 요청
        OauthUserProfile oauthUserProfile = getUserProfile(response.getAccessToken(), provider);
        log.info("profile={}", oauthUserProfile.toString());

        //회원가입 or 업데이트
        User user = saveOrUpdateUser(oauthUserProfile);

        //jwt 토큰 발급
        String jwtToken = generateJwtToken(user.getId());

        return LoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .accessToken(jwtToken)
                .build();
    }

    private String generateJwtToken(Long id) {
        final Claims claims = Jwts.claims();
        claims.put("id", id);

        long now = System.currentTimeMillis();
        long expirationTime = now + 1000 * 60 * 60;

        String result = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        log.info("result={}", result);
        return result;
    }

    private User saveOrUpdateUser(OauthUserProfile oauthUserProfile) {
        User user = userRepository.findByEmail(oauthUserProfile.getEmail())
                .map(u -> u.update(oauthUserProfile))
                .orElseGet(oauthUserProfile::toUser);

        return userRepository.save(user);
    }

    private OauthUserProfile getUserProfile(String accessToken, OauthProvider provider) {
        return RestClient.create()
                .get()
                .uri(provider.getUserProfileUrl())
                .headers(header -> header.setBearerAuth(accessToken))
                .retrieve()
                .body(OauthUserProfile.class);
    }

    private OauthTokenResponse getAccessToken(String code, OauthProvider provider) {
        String response = RestClient.create()
                .post()
                .uri(provider.getTokenUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(buildTokenRequest(code, provider))
                .retrieve()
                .body(String.class);

        try {
            return objectMapper.readValue(response, OauthTokenResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private MultiValueMap<String, String> buildTokenRequest(String code, OauthProvider provider) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("client_id", provider.getClientId());
        map.add("client_secret", provider.getClientSecret());
        map.add("redirect_uri", provider.getRedirectUrl());
        map.add("grant_type", "authorization_code");
        return map;
    }
}
