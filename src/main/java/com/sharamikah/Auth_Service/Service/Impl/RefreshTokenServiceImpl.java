package com.sharamikah.Auth_Service.Service.Impl;


import com.sharamikah.Auth_Service.domain.entity.AppUser;
import com.sharamikah.Auth_Service.domain.entity.RefreshToken;
import com.sharamikah.Auth_Service.Exception.TokenValidationException;
import com.sharamikah.Auth_Service.Repository.RefreshTokenRepository;
import com.sharamikah.Auth_Service.Exception.TokenReuseDetectedException;
import com.sharamikah.Auth_Service.Service.RefreshTokenService;
import com.sharamikah.Auth_Service.Service.SecurityEventLogService;
import com.sharamikah.Auth_Service.domain.entity.AppUser;
import com.sharamikah.Auth_Service.util.JwtTokenProvider;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository tokenRepo;
    private final RedisTemplate<String, String> redis;
    private final SecurityEventLogService audit;
    private final JwtTokenProvider jwtProvider;
    private final Counter createCounter;
    private final Counter validateSuccessCounter;
    private final Counter validateFailureCounter;
    private final Counter revokeCounter;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${security.jwt.refresh-token-ttl-ms}")
    private long refreshTokenTtlMs;

    public RefreshTokenServiceImpl(
            RefreshTokenRepository tokenRepo,
            RedisTemplate<String, String> redis,
            SecurityEventLogService audit,
            JwtTokenProvider jwtProvider,
            MeterRegistry registry
    ) {
        this.tokenRepo       = tokenRepo;
        this.redis           = redis;
        this.audit           = audit;
        this.jwtProvider     = jwtProvider;
        this.createCounter   = registry.counter("auth.refresh.create");
        this.validateSuccessCounter = registry.counter("auth.refresh.validate.success");
        this.validateFailureCounter = registry.counter("auth.refresh.validate.failure");
        this.revokeCounter   = registry.counter("auth.refresh.revoke");
    }

    @Override
    @Transactional
    public String createRefreshToken(AppUser user) {
        String token = generateSecureToken();
        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(refreshTokenTtlMs);

        // persist in DB
        RefreshToken entity = new RefreshToken();
        entity.setToken(token);
        entity.setUser(user);
        entity.setCreatedAt(now);
        entity.setExpiresAt(expiresAt);
        tokenRepo.save(entity);

        // no‐revoke marker in Redis
        String revokeKey = redisKeyRevoked(token);
        redis.delete(revokeKey);

        // record audit & metrics
        audit.logEvent(user.getId(), "TOKEN_CREATED", Map.of("refreshToken", token));
        createCounter.increment();

        return token;
    }

    @Override
    @Transactional
    public boolean revokeByToken(String refreshToken) {
        boolean removed = tokenRepo.findByToken(refreshToken)
                .map(entity -> {
                    tokenRepo.delete(entity);
                    return true;
                })
                .orElse(false);

        // cross‐instance revocation marker
        String revokeKey = redisKeyRevoked(refreshToken);
        redis.opsForValue()
                .set(revokeKey, "true", refreshTokenTtlMs, TimeUnit.MILLISECONDS);

        // audit & metrics
        audit.logEvent(null, "TOKEN_REVOKED", Map.of("refreshToken", refreshToken));
        revokeCounter.increment();

        return removed;
    }

    @Override
    @Transactional
    public Optional<String> validateRefreshToken(String refreshToken) {
        Instant now = Instant.now();
        String revokeKey = redisKeyRevoked(refreshToken);
        String usedKey   = redisKeyUsed(refreshToken);

        // 1) Check cross‐instance revocation
        if (Boolean.TRUE.equals(redis.hasKey(revokeKey))) {
            validateFailureCounter.increment();
            audit.logEvent(null, "TOKEN_REVOKED", Map.of("refreshToken", refreshToken));
            return Optional.empty();
        }

        // 2) Detect reuse
        if (Boolean.TRUE.equals(redis.hasKey(usedKey))) {
            validateFailureCounter.increment();
            audit.logEvent(null, "TOKEN_REUSE_DETECTED", Map.of("refreshToken", refreshToken));
            throw new TokenReuseDetectedException(refreshToken);
        }

        // 3) Load from DB & expiry check
        Optional<RefreshToken> opt = tokenRepo.findByToken(refreshToken)
                .filter(t -> t.getExpiresAt().isAfter(now));
        if (opt.isEmpty()) {
            validateFailureCounter.increment();
            audit.logEvent(null, "TOKEN_INVALID_OR_EXPIRED", Map.of("refreshToken", refreshToken));
            return Optional.empty();
        }

        RefreshToken entity = opt.get();

        // 4) Sliding expiration: push expiry
        Instant newExpiry = now.plusMillis(refreshTokenTtlMs);
        entity.setExpiresAt(newExpiry);
        tokenRepo.save(entity);

        // 5) Mark as used once
        redis.opsForValue()
                .set(usedKey, "true", refreshTokenTtlMs, TimeUnit.MILLISECONDS);

        validateSuccessCounter.increment();
        audit.logEvent(entity.getUser().getId(), "TOKEN_VALIDATED", Map.of("refreshToken", refreshToken));
        return Optional.of(entity.getUser().getEmail());
    }

    @Override
    @Transactional
    public String rotateRefreshToken(String oldToken, AppUser user) {
        // validate + sliding update prevents reuse on oldToken
        validateRefreshToken(oldToken)
                .orElseThrow(() -> new TokenValidationException("Cannot rotate invalid token"));

        // revoke old + issue new
        revokeByToken(oldToken);
        return createRefreshToken(user);
    }

    // secure random 256‐bit URL‐safe token
    private String generateSecureToken() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private String redisKeyRevoked(String token) {
        return "auth:refresh:revoked:" + token;
    }

    private String redisKeyUsed(String token) {
        return "auth:refresh:used:" + token;
    }
}