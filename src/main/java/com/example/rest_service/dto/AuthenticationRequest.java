package com.example.rest_service.dto;

public record AuthenticationRequest(
        String username,
        String password
) {}
