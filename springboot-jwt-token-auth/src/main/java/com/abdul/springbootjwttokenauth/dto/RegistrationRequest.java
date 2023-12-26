package com.abdul.springbootjwttokenauth.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class RegistrationRequest {

    private String name;
    private String email;
    private String password;
}
