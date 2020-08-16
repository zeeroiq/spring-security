package com.spring.security.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
@Data
public class PasswordConfig {

    private final String nameOfBean = "PASSWORD-CONFIG";
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("password encoder called");
        return new BCryptPasswordEncoder(10);
    }
}
