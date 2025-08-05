package com.sharamikah.Auth_Service.Configuration;

import com.sharamikah.Auth_Service.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationProviderConfig {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationProviderConfig(CustomUserDetailsService uds,
                                        PasswordEncoder encoder) {
        this.userDetailsService = uds;
        this.passwordEncoder = encoder;
    }

    /**
     * Used by AuthenticationManager to verify credentials.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
