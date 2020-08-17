package com.spring.security.config.DBsec;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class ApplicationUserDetails implements UserDetails {

    private final Set<? extends GrantedAuthority> authorities;
    private final String username;
    private final String password;
    private final boolean accountNotExpired;
    private final boolean accountNotLocked;
    private final boolean credentialNotExpired;
    private final boolean isEnabled;

    public ApplicationUserDetails(String username,
                                  String password,
                                  Set<? extends GrantedAuthority> authorities,
                                  boolean accountNotExpired,
                                  boolean accountNotLocked,
                                  boolean credentialNotExpired,
                                  boolean isEnabled) {

        this.authorities = authorities;
        this.username = username;
        this.password = password;
        this.accountNotExpired = accountNotExpired;
        this.accountNotLocked = accountNotLocked;
        this.credentialNotExpired = credentialNotExpired;
        this.isEnabled = isEnabled;
    }

    public static ApplicationUserDetailsImplBuilder builder() {
        return new ApplicationUserDetailsImplBuilder();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNotExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }


    public static class ApplicationUserDetailsImplBuilder {
        private Set<? extends GrantedAuthority> authorities;
        private String username;
        private String password;
        private boolean isAccountExpired;
        private boolean isAccountLocked;
        private boolean isCredentialsExpired;
        private boolean isEnabled;

        ApplicationUserDetailsImplBuilder() {
        }

        public ApplicationUserDetailsImplBuilder authorities(Set<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public ApplicationUserDetailsImplBuilder username(String username) {
            this.username = username;
            return this;
        }

        public ApplicationUserDetailsImplBuilder password(String password) {
            this.password = password;
            return this;
        }

        public ApplicationUserDetailsImplBuilder isAccountExpired(boolean isAccountExpired) {
            this.isAccountExpired = isAccountExpired;
            return this;
        }

        public ApplicationUserDetailsImplBuilder isAccountLocked(boolean isAccountLocked) {
            this.isAccountLocked = isAccountLocked;
            return this;
        }

        public ApplicationUserDetailsImplBuilder isCredentialsExpired(boolean isCredentialsExpired) {
            this.isCredentialsExpired = isCredentialsExpired;
            return this;
        }

        public ApplicationUserDetailsImplBuilder isEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        public ApplicationUserDetails build() {
            return new ApplicationUserDetails(username, password, authorities, isAccountExpired, isAccountLocked, isCredentialsExpired, isEnabled);
        }

        public String toString() {
            return "ApplicationUserDetailsImpl.ApplicationUserDetailsImplBuilder" +
                    "(authorities=" + this.authorities
                    + ", username=" + this.username
                    + ", password=" + this.password
                    + ", isAccountExpired=" + this.isAccountExpired
                    + ", isAccountLocked=" + this.isAccountLocked
                    + ", isCredentialsExpired=" + this.isCredentialsExpired
                    + ", isEnabled=" + this.isEnabled
                    + ")";
        }
    }
}
