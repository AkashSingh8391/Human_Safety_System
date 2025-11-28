package com.safety.dto;

public class AuthRequest {
    private String username;
    private String password;
    private String phoneNo;
    private String email;
    // getters/setters
    public AuthRequest(){}
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
