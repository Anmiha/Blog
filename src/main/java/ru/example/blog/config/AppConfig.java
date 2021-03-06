package ru.example.blog.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class AppConfig {
    private final Map<String, Integer> sessions = new HashMap<>();
    private final static int CAPTCHA_LENGTH = 7;
    @Value("${captcha.updateTime:1}")
    private int CAPTCHA_HOURS_TO_BE_UPDATED;
    @Value("${captcha.image.width:100}")
    private int CAPTCHA_WIDTH;
    @Value("${captcha.image.height:35}")
    private int CAPTCHA_HEIGHT;
    @Value("${app.hostname:http://localhost:8080}")
    private String hostname;

    public void addSession(String sessionId, Integer userId) {
        sessions.put(sessionId, userId);
    }

    public int getCaptchaLength() {
        return CAPTCHA_LENGTH;
    }

    public int getCaptchaHoursToBeUpdated() {
        return CAPTCHA_HOURS_TO_BE_UPDATED;
    }

    public int getCaptchaWidth() {
        return CAPTCHA_WIDTH;
    }

    public int getCaptchaHeight() {
        return CAPTCHA_HEIGHT;
    }

    public Map<String, Integer> getSessions() {
        return sessions;
    }

    public Integer getUserIdBySessionId(String sessionId) {
        return sessions.remove(sessionId);
    }

    @Bean("myCacheManager")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(1, TimeUnit.MINUTES)
                                .build().asMap(),
                        false);
            }
        };
    }

    public int getCAPTCHA_HOURS_TO_BE_UPDATED() {
        return CAPTCHA_HOURS_TO_BE_UPDATED;
    }

    public int getCAPTCHA_WIDTH() {
        return CAPTCHA_WIDTH;
    }

    public int getCAPTCHA_HEIGHT() {
        return CAPTCHA_HEIGHT;
    }

    public String getHostname() {
        return hostname;
    }
}
