package com.example.rest_service.service;

import com.example.rest_service.dto.AuthenticationRequest;
import com.example.rest_service.dto.AuthenticationResponse;
import com.example.rest_service.dto.RegisterRequest;
import com.example.rest_service.model.User;
import com.example.rest_service.repository.UserRepository;
import com.example.rest_service.security.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
          UserRepository userRepository,
          JwtService jwtService,
          PasswordEncoder passwordEncoder,
          AuthenticationManager authenticationManager
  ) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  public AuthenticationResponse register(RegisterRequest request) {
    var user = new User();
    user.setUsername(request.username());
    user.setEmail(request.email());
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setRole(Role.ROLE_USER);
    user.setEnabled(true);

//    User savedUser = userRepository.save(user);
    userRepository.save(user);

    var jwtToken = jwtService.generateToken(user);
    return new AuthenticationResponse(jwtToken, user.getUsername());
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            )
    );

    var user = userRepository.findByUsername(request.username())
            .orElseThrow();
    var jwtToken = jwtService.generateToken(user);

    return new AuthenticationResponse(jwtToken, user.getUsername());
  }


}
