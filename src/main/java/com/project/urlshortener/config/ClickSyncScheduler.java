package com.project.urlshortener.config;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.urlshortener.entity.UrlMapping;
import com.project.urlshortener.repository.UrlRepository;
import com.project.urlshortener.service.UrlRedisService;

@Service
public class ClickSyncScheduler {

    private final UrlRepository repository;
    private final UrlRedisService redisService;

    public ClickSyncScheduler(UrlRepository repository,
                              UrlRedisService redisService) {
        this.repository = repository;
        this.redisService = redisService;
    }

    // ⏱ every 60 seconds
    @Scheduled(fixedRate = 60000)
    public void syncClicksToDatabase() {

        List<UrlMapping> allUrls = repository.findAll();

        for (UrlMapping url : allUrls) {

            Long redisClicks = redisService.getClicks(url.getShortCode());

            if (redisClicks > 0) {
                url.setClickCount(redisClicks);
                repository.save(url);
            }
        }

        System.out.println("✅ Clicks synced to MySQL from Redis");
    }
}