package com.sharamikah.Auth_Service.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class RsaKeyConfig {
    @Bean
    public KeyPair rsaKeyPair(
            @Value("${jwt.rsa.private-key}") RSAPrivateKey privateKey,
            @Value("${jwt.rsa.public-key}") RSAPublicKey publicKey
    ) {
        return new KeyPair(publicKey, privateKey);
    }
}
