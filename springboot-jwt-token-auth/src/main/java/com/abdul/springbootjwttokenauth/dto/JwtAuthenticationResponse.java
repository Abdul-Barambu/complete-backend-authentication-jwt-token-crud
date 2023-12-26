package com.abdul.springbootjwttokenauth.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}
