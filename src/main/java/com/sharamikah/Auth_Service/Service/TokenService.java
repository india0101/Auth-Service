//package com.sharamikah.Auth_Service.Service;
//
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.Map;
//
//@Service
//public class TokenService {
//
//    private final RedisTemplate<String, Object> redis;
//    private final Duration accessTtl = Duration.ofMinutes(15);
//    private final Duration refreshTtl = Duration.ofDays(30);
//
//    public TokenService(RedisTemplate<String, Object> redis) {
//        this.redis = redis;
//    }
//
//    public void storeAccessToken(String tokenId, Map<String, Object> payload) {
//        String key = "access_token:" + tokenId;
//        redis.opsForValue().set(key, payload, accessTtl);
//    }
//
//    public void storeRefreshToken(String tokenId, Integer userId, String sessionId) {
//        String key = "refresh_token:" + tokenId;
//        Map<String, Object> data = Map.of(
//                "userId", userId,
//                "sessionId", sessionId,
//                "issuedAt", Instant.now().toString()
//        );
//        redis.opsForHash().putAll(key, data);
//        redis.expire(key, refreshTtl);
//    }
//
//    public boolean isRevoked(String tokenId) {
//        return Boolean.TRUE.equals(redis.opsForSet().isMember("revoked_tokens", tokenId));
//    }
//
//    public void revokeToken(String tokenId) {
//        redis.opsForSet().add("revoked_tokens", tokenId);
//        // Optional: set TTL on revoked_tokens set to auto-prune
//        redis.expire("revoked_tokens", refreshTtl);
//    }
//
//}
