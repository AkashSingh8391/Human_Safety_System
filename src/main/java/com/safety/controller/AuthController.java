package com.safety.controller;

import com.safety.model.User;
import com.safety.service.AuthService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ------------------ Register Endpoint ------------------
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        String response = authService.registerUser(request);
        if(response.equals("User registered successfully"))
            return ResponseEntity.ok(response);
        else 
            return ResponseEntity.badRequest().body(response);
    }

    // ------------------ Login Endpoint ------------------
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        if(token == null)
            return ResponseEntity.status(401).body(new LoginResponse("Invalid credentials"));
        return ResponseEntity.ok(new LoginResponse(token));
    }

}

// ------------------ DTO Classes ------------------
@Data
class LoginRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}

@Data
class LoginResponse {
    private String jwt;
    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }
}

@Data
class RegisterRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Role is required")
    private String role; // CIVIL or POLICE

    private String phoneNo;
}
