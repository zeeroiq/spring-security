package com.shri.springsecurity.spring;

import com.shri.springsecurity.persistence.InMemoryUserRepository;
import com.shri.springsecurity.persistence.UserRepository;
import com.shri.springsecurity.spring.config.CustomSecurityConfig;
import com.shri.springsecurity.web.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;

@SpringBootApplication
@ComponentScan("com.shri.springsecurity.web")
public class SpringSecurityApplication {

    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    public Converter<String, User> messageConverter() {
        return new Converter<String, User>() {
            @Override
            public User convert(String id) {
                return userRepository().findUser(Long.valueOf(id));
            }
        };
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(new Class[] {
                SpringSecurityApplication.class, CustomSecurityConfig.class
        }, args);
    }

}
