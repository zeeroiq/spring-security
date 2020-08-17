package com.spring.security.config.jwt;

import lombok.Data;

@Data
public class UsernamePasswordAuthenticationRequest {

    private String username;
    private String password;
}
