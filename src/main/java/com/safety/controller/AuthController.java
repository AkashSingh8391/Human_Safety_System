package com.safety.controller;

import com.safety.dto.AuthRequest;
import com.safety.dto.AuthResponse;
import com.safety.model.User;
import com.safety.repository.UserRepository;
import com.safety.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private AuthenticationManager authManager;
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest req) {
        if (userRepo.findByEmail(req.getEmail()).isPresent()) return "Email already registered";
        User u = new User();
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepo.save(u);
        return "Registered";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) throws Exception {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Invalid Credentials");
        }
        String token = jwtUtil.generateToken(req.getEmail());
        return new AuthResponse(token);
    }

    @GetMapping("/me")
    public User me(@RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String email = jwtUtil.extractUsername(token);
        return userRepo.findByEmail(email).orElseThrow();
    }

}
