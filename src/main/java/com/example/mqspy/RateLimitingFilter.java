package com.example.mqspy;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimitingFilter implements Filter {

    private FilterConfig filterConfig;
    private Map<String, AtomicInteger> requestCountsPerIpAddress = new ConcurrentHashMap<>();
    private Map<String, LocalDateTime> lastRequestPerIpAddress = new ConcurrentHashMap<>();
    private Map<String, LocalDateTime> timerStartPerIpAddress = new ConcurrentHashMap<>();
    private final int RESET_COUNTER_AFTER_SECONDS = 60;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        int maxRequestPerMinute = Integer.parseInt(filterConfig.getInitParameter("maxRequestPerMinute"));
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String clientIpAddress = request.getRemoteAddr();
        requestCountsPerIpAddress.putIfAbsent(clientIpAddress, new AtomicInteger(0));
        lastRequestPerIpAddress.putIfAbsent(clientIpAddress, LocalDateTime.now());
        timerStartPerIpAddress.putIfAbsent(clientIpAddress, LocalDateTime.now());

        if (LocalDateTime.now().isAfter(timerStartPerIpAddress.get(clientIpAddress).plusSeconds(RESET_COUNTER_AFTER_SECONDS))) {
            timerStartPerIpAddress.put(clientIpAddress, LocalDateTime.now());
            requestCountsPerIpAddress.get(clientIpAddress).set(0);
        }

        lastRequestPerIpAddress.put(clientIpAddress, LocalDateTime.now());
        AtomicInteger requestCount = requestCountsPerIpAddress.get(clientIpAddress);
        int requests = requestCount.incrementAndGet();

        if (requests > maxRequestPerMinute) {
            response.setStatus(429);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
