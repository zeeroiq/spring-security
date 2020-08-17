package com.spring.security.config;

import com.spring.security.config.DBsec.ApplicationUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.spring.security.config.enums.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter {


    public final PasswordEncoder passwordEncoder;
    public final ApplicationUserDetailService applicationUserDetailServiceImplementation;

    @Autowired
    public WebConfig(PasswordEncoder passwordEncoder, ApplicationUserDetailService applicationUserDetailServiceImplementation) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserDetailServiceImplementation = applicationUserDetailServiceImplementation;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
//                .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**")
                .hasRole(STUDENT.name())

                .anyRequest()
                .authenticated()

                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/index", true)
                    .usernameParameter("username")
                    .passwordParameter("password")

                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("reallyverystrongkeytosecurethesessioncookies")
                    .rememberMeParameter("remember-me") // setting up the remember me parameter and cookies name

                .and()
                .logout()
                    .logoutUrl("/logout")
                    // in case CSRF is disabled the below line is suggested by spring team
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
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
