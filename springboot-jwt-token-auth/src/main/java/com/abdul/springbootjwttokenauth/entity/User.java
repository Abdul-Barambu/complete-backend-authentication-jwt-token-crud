package com.abdul.springbootjwttokenauth.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "auth_table")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "auth_sequence", sequenceName = "auth_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_sequence")
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean enabled = false;
    private boolean locked = false;


//    to delete a user from both user and token table, add the following source code below
//start
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private TokenConfirmation tokenConfirmation;

    @PreRemove
    private void preRemove() {
        if (tokenConfirmation != null) {
            tokenConfirmation.setUser(null); // Set the reference to null to prevent cascading from the other side
        }
    }
//    end

    public User(String name,
                String email,
                String password,
                Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
