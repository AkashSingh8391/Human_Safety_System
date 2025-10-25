package com.safety.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "alerts")

public class Alert {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long alertId;


@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "user_id")
private User user;


private String message;
private Double latitude;
private Double longitude;
private boolean active = true;
private Instant createdAt = Instant.now();


@OneToMany(mappedBy = "alert", cascade = CascadeType.ALL, orphanRemoval = true)
private List<AlertLocation> locations = new ArrayList<>();


public Long getAlertId() {
	return alertId;
}


public void setAlertId(Long alertId) {
	this.alertId = alertId;
}


public User getUser() {
	return user;
}


public void setUser(User user) {
	this.user = user;
}


public String getMessage() {
	return message;
}


public void setMessage(String message) {
	this.message = message;
}


public Double getLatitude() {
	return latitude;
}


public void setLatitude(Double latitude) {
	this.latitude = latitude;
}


public Double getLongitude() {
	return longitude;
}


public void setLongitude(Double longitude) {
	this.longitude = longitude;
}


public boolean isActive() {
	return active;
}


public void setActive(boolean active) {
	this.active = active;
}


public Instant getCreatedAt() {
	return createdAt;
}


public void setCreatedAt(Instant createdAt) {
	this.createdAt = createdAt;
}


public List<AlertLocation> getLocations() {
	return locations;
}


public void setLocations(List<AlertLocation> locations) {
	this.locations = locations;
}


public Alert(Long alertId, User user, String message, Double latitude, Double longitude, boolean active,
		Instant createdAt, List<AlertLocation> locations) {
	super();
	this.alertId = alertId;
	this.user = user;
	this.message = message;
	this.latitude = latitude;
	this.longitude = longitude;
	this.active = active;
	this.createdAt = createdAt;
	this.locations = locations;
}


public Alert() {
	super();
	// TODO Auto-generated constructor stub
}




}