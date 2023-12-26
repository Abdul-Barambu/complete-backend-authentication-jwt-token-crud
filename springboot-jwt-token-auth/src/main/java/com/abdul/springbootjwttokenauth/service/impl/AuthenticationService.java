package com.abdul.springbootjwttokenauth.service.impl;

import com.abdul.springbootjwttokenauth.dto.RegistrationRequest;
import com.abdul.springbootjwttokenauth.entity.Role;
import com.abdul.springbootjwttokenauth.entity.TokenConfirmation;
import com.abdul.springbootjwttokenauth.entity.User;
import com.abdul.springbootjwttokenauth.service.EmailValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final EmailValidation emailValidation;
    private final UserService userService;
    private final TokenConfirmationService tokenConfirmationService;

    public ResponseEntity<Object> registerAdmin(RegistrationRequest request) {
        boolean isEmailValid = emailValidation.test(request.getEmail());
        if (!isEmailValid) {
            throw new IllegalStateException("Email is not valid");
        }

        return userService.signUpUsers(
                new User(
                        request.getName(),
                        request.getEmail(),
                        request.getPassword(),
                        Role.ADMIN
                )
        );
    }

    public ResponseEntity<Object> registerSchool(RegistrationRequest request) {
        boolean isEmailValid = emailValidation.test(request.getEmail());
        if (!isEmailValid) {
            throw new IllegalStateException("Email is not valid");
        }

        return userService.signUpUsers(
                new User(
                        request.getName(),
                        request.getEmail(),
                        request.getPassword(),
                        Role.SCHOOL
                )
        );
    }

    public ResponseEntity<Object> registerPayer(RegistrationRequest request) {
        boolean isEmailValid = emailValidation.test(request.getEmail());
        if (!isEmailValid) {
            throw new IllegalStateException("Email is not valid");
        }

        return userService.signUpUsers(
                new User(
                        request.getName(),
                        request.getEmail(),
                        request.getPassword(),
                        Role.PAYER
                )
        );
    }

    //    token
    public ResponseEntity<Object> confirmToken(String confirmToken) {
        TokenConfirmation tokenConfirmation = tokenConfirmationService.getToken(confirmToken)
                .orElseThrow(() -> new IllegalStateException("Token is not found"));

        if (tokenConfirmation.getConfirmedAt() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tokenNotNull("error", "Token already confirmed"));
        }

        LocalDateTime expires = tokenConfirmation.getExpiresAt();
        if (expires.isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tokenNotNull("error", "Token already expired"));
        }

        tokenConfirmationService.setConfirmedAt(confirmToken);
        userService.enabledUser(tokenConfirmation.getUser().getEmail());

        return ResponseEntity.ok(success("success", "confirmed"));

    }

    public Map<String, Object> tokenNotNull(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);
        response.put("data", data);

        return response;
    }

    public Map<String, Object> success(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);
        response.put("data", data);

        return response;
    }

}
