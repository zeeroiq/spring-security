package com.spring.security.config.DBsec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApplicationUserDetailService implements UserDetailsService {

    private final ApplicationUserDAO applicationUserDAO;

    public ApplicationUserDetailService(@Qualifier("fake") ApplicationUserDAO applicationUserDAO) {
        log.debug(">>>>> FAKE APPLICATION USER DAO IS USED : " + applicationUserDAO.getClass().getName());
        this.applicationUserDAO = applicationUserDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDAO
                .selectApplicationUserByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found.", username)));
    }
}
