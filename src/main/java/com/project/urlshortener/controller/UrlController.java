package com.project.urlshortener.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.urlshortener.dto.UrlRequest;
import com.project.urlshortener.dto.UrlResponse;
import com.project.urlshortener.dto.UrlStatsResponse;
import com.project.urlshortener.service.UrlService;

import jakarta.validation.Valid;

@RestController
public class UrlController {

    private final UrlService service;
    
    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping("/api/shorten")
    public ResponseEntity<UrlResponse>
    shortenUrl(
            @Valid
            @RequestBody UrlRequest request){

        return ResponseEntity.ok(
                service.shortenUrl(request));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

    	System.out.println("HIT CONTROLLER: " + shortCode);
    	String url = service.getOriginalUrl(shortCode);

        return ResponseEntity.status(302)
                .location(URI.create(url))
                .build();
    }
    
    @GetMapping("/api/stats/{shortCode}")
    public UrlStatsResponse getStats(@PathVariable String shortCode) {
        return service.getStats(shortCode);
    }
}