package com.safety.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="alerts")
public class Alert {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;
    private Long userId;
    private String message;
    private double latitude;
    private double longitude;
    private Instant timestamp;
    private boolean resolved = false;
    public Alert(){ }
    // getters / setters...
	public Long getAlertId() {
		return alertId;
	}
	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	public boolean isResolved() {
		return resolved;
	}
	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}
    
    
}
