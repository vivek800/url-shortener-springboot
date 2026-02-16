package com.url.shortner.config;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;
    
    public RateLimiterService(StringRedisTemplate redisTemplate)
    {
    	this.redisTemplate = redisTemplate;
    }

    private static final String KEY = "email_rate_limit";
    private static final int LIMIT = 50;

    public boolean tryAcquire() {

        Long count = redisTemplate.opsForValue().increment(KEY);

        if (count == 1) {
            redisTemplate.expire(KEY, Duration.ofSeconds(1));
        }

        return count <= LIMIT;
    }
}
