package com.project.urlshortener.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final int LIMIT = 10; // requests
    private static final long WINDOW = 60; // seconds

    public RateLimiterService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String ip) {

        String key = "rate:" + ip;

        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            redisTemplate.opsForValue()
                    .set(key, "1", Duration.ofSeconds(WINDOW));
            return true;
        }

        int count = Integer.parseInt(value);

        if (count >= LIMIT) {
            return false;
        }

        redisTemplate.opsForValue()
                .increment(key);

        return true;
    }
}