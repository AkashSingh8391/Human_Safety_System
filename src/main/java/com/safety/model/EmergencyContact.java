package com.safety.model;

import jakarta.persistence.*;

@Entity
@Table(name = "emergency_contacts")
public class EmergencyContact {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;    // owner userId
    private String email;   // emergency email (nullable)
    private String phone;   // emergency phone (nullable)

    public EmergencyContact() {}

    public EmergencyContact(Long userId, String email, String phone) {
        this.userId = userId;
        this.email = email;
        this.phone = phone;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
