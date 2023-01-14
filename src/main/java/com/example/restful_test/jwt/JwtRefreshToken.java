package com.example.restful_test.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class JwtRefreshToken {
    private String refreshToken;
}
