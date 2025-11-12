package com.safety.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String username;

    private String password;
    private String role; // "CIVIL" or "POLICE"
    private String phoneNo;

    // 1. Default (No-Args) Constructor
    // Replaces @NoArgsConstructor
    public User() {
    }

    // 2. All-Arguments Constructor
    // Replaces @AllArgsConstructor
    public User(Long userId, String username, String password, String role, String phoneNo) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.phoneNo = phoneNo;
    }

    // 3. Getter Methods
    // Replaces the "getters" part of @Data
    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    // 4. Setter Methods
    // Replaces the "setters" part of @Data
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    // Note: If you need equals(), hashCode(), and toString() (part of @Data), 
    // you would also need to generate those methods here manually.
    // For simplicity, they are omitted in this basic conversion.
}