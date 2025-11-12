package com.safety.dto;

public class AuthRequest {
    private String username;
    private String password;
    private String role; // optional for register

    // 1. Default (No-Args) Constructor
    // Replaces the implicit constructor from @Data or @NoArgsConstructor (if present)
    public AuthRequest() {
    }

    // 2. Getter Methods
    // Replaces the "getters" part of @Data
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // 3. Setter Methods
    // Replaces the "setters" part of @Data
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    // Note: @Data also adds toString(), equals(), and hashCode(). 
    // You would need to generate those manually if they are required.
}