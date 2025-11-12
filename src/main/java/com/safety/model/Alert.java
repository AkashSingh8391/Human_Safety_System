package com.safety.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    private Long userId;
    private String message;
    private double latitude;
    private double longitude;
    private Instant timestamp;
    private boolean resolved = false;

    // 1. Default (No-Args) Constructor
    // Replaces @NoArgsConstructor
    public Alert() {
    }

    // 2. All-Arguments Constructor
    // Replaces @AllArgsConstructor
    public Alert(Long alertId, Long userId, String message, double latitude, double longitude, Instant timestamp, boolean resolved) {
        this.alertId = alertId;
        this.userId = userId;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.resolved = resolved;
    }

    // 3. Getter Methods
    // Replaces the "getters" part of @Data
    public Long getAlertId() {
        return alertId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    // Note: For a boolean field named 'resolved', the getter is typically 'isResolved()'
    public boolean isResolved() {
        return resolved;
    }

    // 4. Setter Methods
    // Replaces the "setters" part of @Data
    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}