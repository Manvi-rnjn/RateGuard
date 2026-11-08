package com.rateguard.core.dto;

public record ClientRegistrationRequest(
        String clientName,
        String tier,
        int rateLimit
) {}
