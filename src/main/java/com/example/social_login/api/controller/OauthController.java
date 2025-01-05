package com.example.social_login.api.controller;

import com.example.social_login.api.controller.dto.response.LoginResponse;
import com.example.social_login.api.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/redirect/oauth/{provider}")
    public ResponseEntity<LoginResponse> login(@PathVariable("provider") String provider, @RequestParam("code") String code) {
        LoginResponse loginResponse = oauthService.login(provider, code);
        return ResponseEntity.ok().body(loginResponse);
    }
}
