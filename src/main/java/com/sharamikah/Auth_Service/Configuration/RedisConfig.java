//package com.sharamikah.Auth_Service.Configuration;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//public class RedisConfig {
//
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory(
//            @Value("${spring.redis.host}") String host,
//            @Value("${spring.redis.port}") int port) {
//        return new LettuceConnectionFactory(host, port);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(
//            RedisConnectionFactory cf) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(cf);
//
//        // Use JSON serialization for values
//        Jackson2JsonRedisSerializer<Object> serializer =
//                new Jackson2JsonRedisSerializer<>(Object.class);
//        template.setValueSerializer(serializer);
//        template.setHashValueSerializer(serializer);
//
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//        return template;
//    }
//}
