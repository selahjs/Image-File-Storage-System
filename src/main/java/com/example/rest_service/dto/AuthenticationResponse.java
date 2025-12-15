package com.example.rest_service.dto;

public record AuthenticationResponse(
        String token,
        String username
) {}
