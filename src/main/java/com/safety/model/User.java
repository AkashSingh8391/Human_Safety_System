package com.safety.model;

import jakarta.persistence.*;



@Entity
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long userId;


@Column(unique = true, nullable = false)
private String username;


@Column(nullable = false)
private String password;


private String role; // CIVIL or POLICE
private String phoneNo;
private boolean enabled = true;
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
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
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getPhoneNo() {
	return phoneNo;
}
public void setPhoneNo(String phoneNo) {
	this.phoneNo = phoneNo;
}
public boolean isEnabled() {
	return enabled;
}
public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}
public User(Long userId, String username, String password, String role, String phoneNo, boolean enabled) {
	super();
	this.userId = userId;
	this.username = username;
	this.password = password;
	this.role = role;
	this.phoneNo = phoneNo;
	this.enabled = enabled;
}
public User() {
	super();
	// TODO Auto-generated constructor stub
}



}