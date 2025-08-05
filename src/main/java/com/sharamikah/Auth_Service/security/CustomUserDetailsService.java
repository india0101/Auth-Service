package com.sharamikah.Auth_Service.security;


import com.sharamikah.Auth_Service.Exception.UserNotFoundException;
import com.sharamikah.Auth_Service.Repository.UserRepository;
import com.sharamikah.Auth_Service.domain.entity.AppUser;
import com.sharamikah.Auth_Service.util.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService
        implements UserDetailsService, JwtUserDetailsService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtProvider;

    public CustomUserDetailsService(UserRepository userRepository,
                                    JwtTokenProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider   = jwtProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found: " + email));

        boolean active = user.getAccountStatus() == null
                || user.getAccountStatus().name().equals("ACTIVE");

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                active, true, true, true,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Override
    public Authentication getAuthenticationFromToken(String token) {
        // 1. Validate the token
        if (!jwtProvider.validateToken(token)) {
            throw new BadCredentialsException("Invalid JWT token");
        }

        // 2. Extract email
        String email = jwtProvider.getEmailFromToken(token);

        // 3. Load userDetails
        UserDetails userDetails = loadUserByUsername(email);

        // 4. Build Authentication
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        // 5. (Optional) attach request details later in filter
        return auth;
    }
}