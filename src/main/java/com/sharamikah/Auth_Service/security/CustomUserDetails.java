package com.sharamikah.Auth_Service.security;


import com.sharamikah.Auth_Service.domain.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Adapts our User entity to Spring Security's UserDetails.
 */
public class CustomUserDetails implements UserDetails {
    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final boolean enabled;
    private final List<GrantedAuthority> authorities;
    private final AppUser user;

    public CustomUserDetails(Long id,
                             String username,
                             String email,
                             String password,
                             boolean enabled,
                             AppUser user,
                             List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.user = user;
        this.authorities = authorities;
    }

    /**
     * Factory method to build CustomUserDetails from User entity.
     */
    public static CustomUserDetails fromUserEntity(AppUser user) {
        List<GrantedAuthority> auths = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                auths
        );
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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

    // All accounts are non-expired/non-locked/credentials-valid in this example.
    // Adjust if you have fields like accountNonLocked, credentialsNonExpired, etc.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // Prefix roles with "ROLE_"
        user.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()))
        );

        // Add each permission directly
        user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .forEach(perm ->
                        authorities.add(new SimpleGrantedAuthority(perm.getName()))
                );

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
