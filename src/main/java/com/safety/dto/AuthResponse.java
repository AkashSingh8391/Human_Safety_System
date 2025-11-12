package com.safety.dto;

public class AuthResponse {
    private String token;

    // 1. Default (No-Args) Constructor
    // Required for many frameworks, though not explicitly requested by Lombok annotations here.
    public AuthResponse() {
    }

    // 2. All-Arguments Constructor
    // Replaces @AllArgsConstructor
    public AuthResponse(String token) {
        this.token = token;
    }

    // 3. Getter Method
    // Replaces the "getter" part of @Data
    public String getToken() {
        return token;
    }

    // 4. Setter Method
    // Replaces the "setter" part of @Data
    public void setToken(String token) {
        this.token = token;
    }

    // Note: @Data also adds toString(), equals(), and hashCode(). 
    // You would need to generate those manually if required.
}