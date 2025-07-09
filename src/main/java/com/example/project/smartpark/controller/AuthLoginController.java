package com.example.project.smartpark.controller;

import com.example.project.smartpark.Util.JwtUtil;
import com.example.project.smartpark.model.LoginRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthLoginController {
    private static final Logger logger = LoggerFactory.getLogger(AuthLoginController.class);

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(request.getUsername(), request.getPassword());
        authenticationManager.authenticate(authenticationRequest);

        logger.info("Creating token");
        String jwt = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok().body("Bearer " + jwt);
    }
}
