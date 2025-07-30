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
//public class SessionService {
//
//    private final RedisTemplate<String, Object> redis;
//    private final Duration sessionTtl = Duration.ofDays(30);
//
//    public SessionService(RedisTemplate<String, Object> redis) {
//        this.redis = redis;
//    }
//
//    public void createSession(Integer userId, String sessionId, String ip, String device) {
//        String key = "session:" + userId + ":" + sessionId;
//        Map<String, Object> data = Map.of(
//                "ip_address", ip,
//                "device_name", device,
//                "createdAt", Instant.now().toString()
//        );
//        redis.opsForHash().putAll(key, data);
//        redis.expire(key, sessionTtl);
//    }
//}
