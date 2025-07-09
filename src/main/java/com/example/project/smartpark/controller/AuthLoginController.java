package com.example.project.smartpark.controller;

import com.example.project.smartpark.Util.JwtUtil;
import com.example.project.smartpark.model.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthLoginController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(request.getUsername(), request.getPassword());
        authenticationManager.authenticate(authenticationRequest);

        String jwt = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok().body("Bearer " + jwt);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Bearer ");
    }
}
