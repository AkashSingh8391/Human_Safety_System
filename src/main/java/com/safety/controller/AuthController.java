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
        if (userRepo.findByUsername(req.getUsername()).isPresent()) return "Username taken";
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setEmergencyEmail(req.getEmergencyEmail());
        u.setEmergencyPhone(req.getEmergencyPhone());
        userRepo.save(u);
        return "Registered";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) throws Exception {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Invalid Credentials");
        }
        String token = jwtUtil.generateToken(req.getUsername());
        return new AuthResponse(token);
    }
    
    @GetMapping("/me")
    public User me(@RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwtUtil.extractUsername(token);
        return userRepo.findByUsername(username).orElseThrow();
    }

}
