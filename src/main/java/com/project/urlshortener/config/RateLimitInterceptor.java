package com.project.urlshortener.config;

import com.project.urlshortener.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;

    public RateLimitInterceptor(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        String ip = request.getRemoteAddr();

        boolean allowed = rateLimiterService.isAllowed(ip);

        if (!allowed) {
            response.setStatus(429); // Too Many Requests
            response.getWriter()
                    .write("Rate limit exceeded. Try again later.");
            return false;
        }

        return true;
    }
}