package com.abdul.springbootjwttokenauth.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "token_auth_table")
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class TokenConfirmation {

    @Id
    @SequenceGenerator(name = "token_auth_sequence", sequenceName = "token_auth_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_auth_sequence")
    private Long id;
    private String confirmToken;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public TokenConfirmation(String confirmToken,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             User user) {
        this.confirmToken = confirmToken;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
