package com.safety.controller;

import com.safety.model.User;
import com.safety.service.AuthService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String response = authService.registerUser(user);
        if(response.equals("User registered successfully"))
            return ResponseEntity.ok(response);
        else 
            return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.username, loginRequest.password);
        if(token == null)
            return ResponseEntity.status(401).build();
        return ResponseEntity.ok(new LoginResponse(token));
    }
}

@Data
class LoginRequest {
    public String username;
    public String password;
}

@Data
class LoginResponse {
    private String jwt;
    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }
}
