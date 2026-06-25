package com.project.urlshortener.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.urlshortener.dto.UrlRequest;
import com.project.urlshortener.dto.UrlResponse;
import com.project.urlshortener.dto.UrlStatsResponse;
import com.project.urlshortener.entity.UrlMapping;
import com.project.urlshortener.exception.ResourceNotFoundException;
import com.project.urlshortener.repository.UrlRepository;

@Service
public class UrlService {

    private final UrlRepository repository;
    private final UrlRedisService redisService;

    // Constructor Injection 
    public UrlService(UrlRepository repository,  UrlRedisService redisService) {
        this.repository = repository;
        this.redisService = redisService;
    }

    public UrlResponse shortenUrl(UrlRequest request) {

        String code = UUID.randomUUID()
                .toString()
                .substring(0, 8);

        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(request.getOriginalUrl());
        mapping.setShortCode(code);
        mapping.setClickCount(0L);

        repository.save(mapping);

        return new UrlResponse(
                request.getOriginalUrl(),
                "http://localhost:8080/" + code
        );
    }

    public String getOriginalUrl(String shortCode) {

        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("URL Not Found"));

        // ONLY Redis increment (no DB write per request)
        redisService.incrementClick(shortCode);

        return mapping.getOriginalUrl();
    }
    
    public UrlStatsResponse getStats(String shortCode) {

        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("URL Not Found"));

        Long redisClicks = redisService.getClicks(shortCode);

        return new UrlStatsResponse(
                mapping.getOriginalUrl(),
                mapping.getShortCode(),
                redisClicks
        );
    }
}