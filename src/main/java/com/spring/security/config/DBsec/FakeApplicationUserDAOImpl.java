package com.spring.security.config.DBsec;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.spring.security.config.enums.ApplicationUserRole.*;

@Slf4j
@Repository("fake")
public class FakeApplicationUserDAOImpl implements ApplicationUserDAO {

    private final PasswordEncoder passwordEncoder;

    public FakeApplicationUserDAOImpl(PasswordEncoder passwordEncoder) {
        log.info(">>>>> Password encoder class : " + passwordEncoder.getClass().getName());
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUserDetails> selectApplicationUserByName(String username) {
        return applicationUsers().stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUserDetails> applicationUsers() {
        List<ApplicationUserDetails> applicationUsers = Lists.newArrayList(
                new ApplicationUserDetails("Popeye",
                        passwordEncoder.encode("password"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),

                new ApplicationUserDetails("Chhota_Bheem",
                        passwordEncoder.encode("password"),
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),

                new ApplicationUserDetails("chachaChaudhary",
                        passwordEncoder.encode("password"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true)
                );

        return applicationUsers;
    }

    private List<ApplicationUserDetails> getApplicationUsers() {
        List<ApplicationUserDetails> applicationUsers = Lists.newArrayList(
                ApplicationUserDetails.builder()
                        .username("Popeye")
                        .password(passwordEncoder.encode("password"))
                        .authorities(ADMIN.getGrantedAuthorities())
                        .isAccountExpired(true)
                        .isAccountLocked(true)
                        .isCredentialsExpired(true)
                        .isEnabled(true)
                        .build(),

                ApplicationUserDetails
                        .builder()
                        .username("Chhota_Bheem")
                        .password(passwordEncoder.encode("password"))
                        .authorities(STUDENT.getGrantedAuthorities())
                        .isAccountExpired(true)
                        .isAccountLocked(true)
                        .isCredentialsExpired(true)
                        .isEnabled(true)
                        .build(),

                ApplicationUserDetails
                        .builder()
                        .username("chachaChaudhary")
                        .password(passwordEncoder.encode("password"))
                        .authorities(ADMIN.getGrantedAuthorities())
                        .isAccountExpired(true)
                        .isAccountLocked(true)
                        .isCredentialsExpired(true)
                        .isEnabled(true)
                        .build()
        );

        return applicationUsers;
    }
}
