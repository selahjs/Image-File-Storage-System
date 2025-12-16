package com.example.rest_service.controller;

import com.example.rest_service.dto.AuthenticationRequest;
import com.example.rest_service.dto.AuthenticationResponse;
import com.example.rest_service.dto.RegisterRequest;
import com.example.rest_service.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final AuthenticationService authService;

  public UserController(AuthenticationService authService) {
    this.authService = authService;
  }
  /**
   * Endpoint for user registration (creating a new account).
   * Returns a JWT on success (auto-login).
   */
  @GetMapping()
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
    AuthenticationResponse response = authService.register(request);
    return ResponseEntity.ok(response);
  }

  /**
   * Endpoint for user login (authentication).
   * Returns a JWT on success.
   */
  @GetMapping("/{id}")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
    AuthenticationResponse response = authService.authenticate(request);
    return ResponseEntity.ok(response);
  }
}

