package com.abdul.springbootjwttokenauth.service.impl;

import com.abdul.springbootjwttokenauth.entity.TokenConfirmation;
import com.abdul.springbootjwttokenauth.entity.User;
import com.abdul.springbootjwttokenauth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenConfirmationService tokenConfirmationService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("Email not found"));
    }

    public ResponseEntity<Object> signUpUsers(User user) {
        boolean emailExist = userRepository.findByEmail(user.getEmail()).isPresent();
        if (emailExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailExist("error", "Email already exits"));
        }

        String passwordEncoded = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);

        userRepository.save(user);

//        Token
        String confirmToken = UUID.randomUUID().toString();

        TokenConfirmation tokenConfirmation = new TokenConfirmation(
                confirmToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                user
        );

        tokenConfirmationService.saveToken(tokenConfirmation);

        return ResponseEntity.ok(success("success", "registration successfully and your token is " + confirmToken));
    }

    public int enabledUser(String email) {
        return userRepository.enabledUser(email);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // response messages
    public Map<String, Object> emailExist(String status, String message) {
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



