package com.abdul.springbootjwttokenauth.repository;

import com.abdul.springbootjwttokenauth.entity.TokenConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenConfirmationRepository extends JpaRepository<TokenConfirmation, Long> {

    Optional<TokenConfirmation> findByConfirmToken(String confirmToken);

    @Transactional
    @Modifying
    @Query("UPDATE TokenConfirmation tc SET tc.confirmedAt = ?2 WHERE tc.confirmToken = ?1")
    int updateConfirmedAt(String confirmToken, LocalDateTime confirmedAt);
}
