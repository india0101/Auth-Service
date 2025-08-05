package com.sharamikah.Auth_Service.util;


import com.sharamikah.Auth_Service.domain.entity.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-token-expiration-ms}")
    private long accessTokenExpirationMs;

    private Key signingKey;

    @PostConstruct
    public void init() {
        // Decode secret and create an HMAC‐SHA key
        signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate a signed JWT access token per RFC 7519.
     * - Subject: user’s email
     * - IssuedAt: now
     * - Expiration: now + configured expiry
     */
    public String generateAccessToken(AppUser user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenExpirationMs);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Validate signature and expiry of the JWT.
     * @throws JwtException if token is invalid/expired/malformed
     */
    public boolean validateToken(String token) {
        // will throw a subclass of JwtException on failure
        Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token);
        return true;
    }

    /**
     * How many seconds remain until access tokens expire.
     */
    public long getAccessTokenExpirySeconds() {
        return accessTokenExpirationMs / 1_000;
    }

    /**
     * (Optional) Extract subject (email) from token.
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(rsaKeyPair.getPrivate(), SignatureAlgorithm.RS256)
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(rsaKeyPair.getPublic())
                .build()
                .parseClaimsJws(token);
    }
}
