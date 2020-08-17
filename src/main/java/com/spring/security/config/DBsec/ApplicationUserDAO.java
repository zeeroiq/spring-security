package com.spring.security.config.DBsec;


import java.util.Optional;

public interface ApplicationUserDAO {

    Optional<ApplicationUserDetails> selectApplicationUserByName(String username);
}
