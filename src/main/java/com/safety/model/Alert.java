package com.safety.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String message;

    private double latitude;
    private double longitude;

    private LocalDateTime timestamp;

    private boolean resolved = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public Alert(Long id, User user, String message, double latitude, double longitude, LocalDateTime timestamp,
			boolean resolved) {
		super();
		this.id = id;
		this.user = user;
		this.message = message;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timestamp = timestamp;
		this.resolved = resolved;
	}

	public Alert() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
	
}
