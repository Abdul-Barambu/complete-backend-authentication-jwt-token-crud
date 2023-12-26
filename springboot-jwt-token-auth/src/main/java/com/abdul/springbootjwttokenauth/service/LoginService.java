package com.abdul.springbootjwttokenauth.service;

import com.abdul.springbootjwttokenauth.dto.JwtAuthenticationResponse;
import com.abdul.springbootjwttokenauth.dto.LoginRequest;

public interface LoginService {

    JwtAuthenticationResponse signIn(LoginRequest loginRequest);
}
