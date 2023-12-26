package com.abdul.springbootjwttokenauth.service.impl;

import com.abdul.springbootjwttokenauth.entity.TokenConfirmation;
import com.abdul.springbootjwttokenauth.repository.TokenConfirmationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenConfirmationService {

    private final TokenConfirmationRepository tokenConfirmationRepository;


    public void saveToken(TokenConfirmation tokenConfirmation){
        tokenConfirmationRepository.save(tokenConfirmation);
    }

    public Optional<TokenConfirmation> getToken(String confirmToken){
        return tokenConfirmationRepository.findByConfirmToken(confirmToken);
    }

    public int setConfirmedAt(String confirmToken){
        return tokenConfirmationRepository.updateConfirmedAt(confirmToken, LocalDateTime.now());
    }
}
