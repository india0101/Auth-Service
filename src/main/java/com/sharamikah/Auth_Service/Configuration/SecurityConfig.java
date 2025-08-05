package com.sharamikah.Auth_Service.Configuration;

import com.sharamikah.Auth_Service.security.JwtAuthenticationFilter;
import com.sharamikah.Auth_Service.security.RestAuthenticationEntryPoint;
import com.sharamikah.Auth_Service.util.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtProvider;

    public SecurityConfig(UserDetailsService uds,
                          PasswordEncoder encoder, JwtTokenProvider jwtProvider) {
        this.userDetailsService = uds;
        this.passwordEncoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    // httpSecurity configuration omitted for brevity


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtProvider);
    }

    @Bean
    public RestAuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint(authenticationEntryPoint())
                )
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

