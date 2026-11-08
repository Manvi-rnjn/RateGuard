package com.rateguard.core.controller;

import com.rateguard.core.dto.ClientRegistrationRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/api/v1/health")
    public Map<String, String> healthCheck() {
        return Map.of(
                "status", "UP",
                "service", "RateGuard-Core"
        );
    }

    @GetMapping("/api/v1/echo/{name}")
    public Map<String , Object> echo(@PathVariable("name") String name) {
        return Map.of(
            "message", "Hello, "+ name + "!",
            "timestamp", System.currentTimeMillis()
        );
    }

    @GetMapping("/api/v1/greet/{name}")
    public Map<String, Object> echo(@PathVariable("name") String name, @RequestParam(value = "greeting", defaultValue = "Hello") String greeting) {
        return Map.of(
                "message",greeting+", "+name+"!",
                "timestamp",System.currentTimeMillis()
        );
    }

    @PostMapping("/api/v1/clients")
    public Map<String , Object> echo(@RequestBody ClientRegistrationRequest request){
        return Map.of(
                "status", "registered",
                "client", request.clientName(),
                "allocatedRateLimit", request.rateLimit(),
                "registeredAt", System.currentTimeMillis()
        );
    }
}

