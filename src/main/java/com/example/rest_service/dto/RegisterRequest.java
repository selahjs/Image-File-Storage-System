package com.example.rest_service.dto;

public record RegisterRequest(
        String username,
        String email,
        String password
) {}
