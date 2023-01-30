package com.example.restful_test.service;

import com.example.restful_test.model.AccessToken;
import com.example.restful_test.model.RefreshToken;
import com.example.restful_test.model.User;
import com.example.restful_test.jwt.JwtProvider;
import com.example.restful_test.jwt.JwtRequest;
import com.example.restful_test.jwt.JwtResponse;
import com.example.restful_test.repository.AccessTokenRepository;
import com.example.restful_test.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final Map<String, String> refreshStorage = new HashMap<>();

    public JwtResponse login (JwtRequest authRequest) {
        User user = userService.findByLogin(authRequest.getLogin());
        System.out.println(user);
        if (user.getPassword().equals(authRequest.getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);
            RefreshToken refreshToken1 = new RefreshToken(user.getLogin(), refreshToken, false);
            AccessToken accessToken1 = new AccessToken(user.getLogin(), accessToken, false);
            refreshTokenRepository.save(refreshToken1);
            accessTokenRepository.save(accessToken1);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            System.out.println("Неправильный пароль");
            return null;
        }
    }

    public JwtResponse getAccessToken (String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                User user = userService.findByLogin(login);
                String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                User user = userService.findByLogin(login);
                String accessToken = jwtProvider.generateAccessToken(user);
                String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getLogin(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        return null;
    }
}
