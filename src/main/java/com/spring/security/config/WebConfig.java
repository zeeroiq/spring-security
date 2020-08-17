package com.spring.security.config;

import com.spring.security.config.DBsec.ApplicationUserDetailService;
import com.spring.security.config.jwt.JwtConfig;
import com.spring.security.config.jwt.JwtRequestVerifier;
import com.spring.security.config.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static com.spring.security.config.enums.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter {


    public final PasswordEncoder passwordEncoder;
    public final ApplicationUserDetailService applicationUserDetailServiceImplementation;
    private final JwtConfig jwtConfig;
    private final SecretKey jwtSecretKey;

    @Autowired
    public WebConfig(PasswordEncoder passwordEncoder, ApplicationUserDetailService applicationUserDetailServiceImplementation, JwtConfig jwtConfig, SecretKey jwtSecretKey) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserDetailServiceImplementation = applicationUserDetailServiceImplementation;
        this.jwtConfig = jwtConfig;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig, jwtSecretKey))
                    .addFilterAfter(new JwtRequestVerifier(jwtSecretKey, jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                    .antMatchers("/api/**")
                    .hasRole(STUDENT.name())
                    .anyRequest()
                    .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(applicationUserDetailServiceImplementation);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

}
