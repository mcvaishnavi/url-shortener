package com.project.urlshortener.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UrlRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public UrlRedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Increment click in Redis (FAST)
    public void incrementClick(String shortCode) {
        redisTemplate.opsForValue()
                .increment("clicks:" + shortCode);
    }

    // Get current clicks
    public Long getClicks(String shortCode) {
        String value = redisTemplate.opsForValue()
                .get("clicks:" + shortCode);

        return value == null ? 0L : Long.parseLong(value);
    }
}