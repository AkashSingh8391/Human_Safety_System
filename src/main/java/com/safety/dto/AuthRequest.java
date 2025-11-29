package com.safety.dto;
public class AuthRequest {
    private String username;
    private String password;
    private String emergencyEmail;
    private String emergencyPhone;
    // getters & setters
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmergencyEmail() {
		return emergencyEmail;
	}
	public void setEmergencyEmail(String emergencyEmail) {
		this.emergencyEmail = emergencyEmail;
	}
	public String getEmergencyPhone() {
		return emergencyPhone;
	}
	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}
    
    
}
