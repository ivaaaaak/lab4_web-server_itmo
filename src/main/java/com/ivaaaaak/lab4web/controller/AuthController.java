package com.ivaaaaak.lab4web.controller;

import com.ivaaaaak.lab4web.controller.requests.AuthJwtRequest;
import com.ivaaaaak.lab4web.controller.requests.RefreshJwtRequest;
import com.ivaaaaak.lab4web.controller.responses.JwtResponse;
import com.ivaaaaak.lab4web.security.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid AuthJwtRequest request) {
        try {
            log.info("Login request: {}", request);
            return ResponseEntity.ok(authService.login(request));
        } catch (AuthException e) {
            log.info("Login error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AuthJwtRequest request) {
        if (authService.checkIfUserExists(request.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exist");
        }

        final var newUser = authService.register(request);
        if (newUser != null) {
            log.info("Registered user: {}", newUser);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody @Valid RefreshJwtRequest request) {
        try {
            final var accessToken = authService.getNewAccessToken(request.refreshToken());
            return ResponseEntity.ok(accessToken);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody @Valid RefreshJwtRequest request) {
        try {
            final var response = authService.refresh(request.refreshToken());
            return ResponseEntity.ok(response);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
