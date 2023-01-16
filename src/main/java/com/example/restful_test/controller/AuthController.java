package com.example.restful_test.controller;

import com.example.restful_test.jwt.JwtRefreshToken;
import com.example.restful_test.jwt.JwtRequest;
import com.example.restful_test.jwt.JwtResponse;
import com.example.restful_test.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = authService.login(jwtRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody JwtRefreshToken jwtRequest) {
        JwtResponse jwtResponse = authService.getAccessToken(jwtRequest.getRefreshToken());
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh (@RequestBody JwtRefreshToken jwtRequest) {
        JwtResponse jwtResponse = authService.refresh(jwtRequest.getRefreshToken());
        return ResponseEntity.ok(jwtResponse);
    }

}
