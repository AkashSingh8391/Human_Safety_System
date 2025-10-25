package com.safety.model;


import jakarta.persistence.*;


import java.time.Instant;


@Entity
@Table(name = "alert_locations")

public class AlertLocation {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "alert_id")
private Alert alert;


private Double latitude;
private Double longitude;
private Instant timestamp = Instant.now();
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public Alert getAlert() {
	return alert;
}
public void setAlert(Alert alert) {
	this.alert = alert;
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
public Instant getTimestamp() {
	return timestamp;
}
public void setTimestamp(Instant timestamp) {
	this.timestamp = timestamp;
}
public AlertLocation() {
	super();
	// TODO Auto-generated constructor stub
}
public AlertLocation(Long id, Alert alert, Double latitude, Double longitude, Instant timestamp) {
	super();
	this.id = id;
	this.alert = alert;
	this.latitude = latitude;
	this.longitude = longitude;
	this.timestamp = timestamp;
}



}