package com.project.urlshortener.dto;

public class UrlStatsResponse {

    private String originalUrl;
    private String shortCode;
    private Long clickCount;

    public UrlStatsResponse(String originalUrl, String shortCode, Long clickCount) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.clickCount = clickCount;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public Long getClickCount() {
        return clickCount;
    }
}