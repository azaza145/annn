package com.example.controller;

import com.example.dto.AuthenticationRequest;
import com.example.exception.InvalidCredentialsException;
import com.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;



    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest authRequest) {
        try {
            // Try to authenticate the user with provided credentials
            String token = authService.authenticate(authRequest.getUsername(), authRequest.getPassword());

            // Return token if successful
            return ResponseEntity.ok(token);

        } catch (InvalidCredentialsException e) {
            // Log the failed attempt (if not logged in the service)
            Logger logger = LoggerFactory.getLogger(AuthController.class);
            logger.warn("Authentication failed for user: {}", authRequest.getUsername());

            // Handle incorrect credentials, returning 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        } catch (RuntimeException e) {
            // Catch the case where the user is not found or other RuntimeExceptions
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}