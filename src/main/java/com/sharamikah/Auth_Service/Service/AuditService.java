//package com.sharamikah.Auth_Service.Service;
//
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.util.Map;
//
//@Service
//public class AuditService {
//
//    private final RedisTemplate<String, Object> redis;
//
//    public AuditService(RedisTemplate<String, Object> redis) {
//        this.redis = redis;
//    }
//
//    public void pushLoginEvent(Integer userId, LoginEvent event) {
//        String listKey = "login_history:" + userId;
//        redis.opsForList().leftPush(listKey, event);
//        redis.opsForList().trim(listKey, 0, 9); // keep last 10 events
//    }
//
//    public void recordConsent(Integer userId, Instant terms, Instant privacy) {
//        String hashKey = "consent:" + userId;
//        Map<String, Object> data = Map.of(
//                "terms_accepted_at", terms.toString(),
//                "privacy_policy_accepted_at", privacy.toString()
//        );
//        redis.opsForHash().putAll(hashKey, data);
//    }
//}
