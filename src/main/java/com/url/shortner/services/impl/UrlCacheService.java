package com.url.shortner.services.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UrlCacheService {

    private static final String KEY_PREFIX = "short_url:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void save(String shortCode, String longUrl) {
        redisTemplate.opsForValue()
                .set(KEY_PREFIX + shortCode, longUrl, Duration.ofHours(24));
    }

    public String get(String shortCode) {
        return redisTemplate.opsForValue()
                .get(KEY_PREFIX + shortCode);
    }

    public void delete(String shortCode) {
        redisTemplate.delete(KEY_PREFIX + shortCode);
    }
}