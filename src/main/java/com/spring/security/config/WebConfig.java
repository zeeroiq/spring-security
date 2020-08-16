package com.spring.security.config;

import com.spring.security.config.com.spring.sercurity.entity.enums.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.spring.security.config.com.spring.sercurity.entity.enums.ApplicationUserPermission.COURSE_READ;
import static com.spring.security.config.com.spring.sercurity.entity.enums.ApplicationUserPermission.COURSE_WRITE;
import static com.spring.security.config.com.spring.sercurity.entity.enums.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return super.userDetailsServiceBean();
//    }

    public final PasswordEncoder passwordEncoder;

    public WebConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() {
        UserDetails popeye = User.builder()
                .username("Popeye")
                .password(passwordEncoder.encode("password"))
                .roles(ADMIN.name())
                .build();

        UserDetails chhotaBheem = User.builder()
                .username("Chhota_Bheem")
                .password(passwordEncoder.encode("password"))
                .roles(STUDENT.name())
                .build();

        UserDetails chachaChaudhary = User.builder()
                .username("chachaChaudhary")
                .password(passwordEncoder.encode("password"))
                .roles(ADMIN_TRAINEE.name())
                .build();

        return new InMemoryUserDetailsManager(popeye, chhotaBheem, chachaChaudhary);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/**")
                    .hasRole(STUDENT.name())

                .antMatchers(HttpMethod.DELETE, "/management/api/**")
                    .hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.POST, "/management/api/**")
                    .hasAuthority(COURSE_READ.name())
                .antMatchers(HttpMethod.PUT, "/management/api/**")
                    .hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.GET, "/management/api/**")
                    .hasAnyAuthority(ADMIN.name(), ADMIN_TRAINEE.name())

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
}
