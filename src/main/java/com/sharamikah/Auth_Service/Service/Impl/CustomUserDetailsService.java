package com.sharamikah.Auth_Service.Service.Impl;

import com.sharamikah.Auth_Service.Repository.UserRepository;
import com.sharamikah.Auth_Service.domain.entity.AppUser;
import com.sharamikah.Auth_Service.security.CustomUserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AppUser user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserPrincipal(user);
    }
}
