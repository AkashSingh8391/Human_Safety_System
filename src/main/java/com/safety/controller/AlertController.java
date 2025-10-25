package com.safety.controller;

import com.safety.model.Alert;
import com.safety.service.AlertService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @PostMapping("/sos")
    public ResponseEntity<Alert> sendAlert(@RequestBody AlertRequest alertRequest) {
        Alert alert = alertService.createAlert(
            alertRequest.userId, alertRequest.message, alertRequest.latitude, alertRequest.longitude);
        return ResponseEntity.ok(alert);
    }

    @GetMapping("/police")
    public ResponseEntity<List<Alert>> getActiveAlerts() {
        List<Alert> alerts = alertService.getActiveAlerts();
        return ResponseEntity.ok(alerts);
    }

    @PutMapping("/resolve/{id}")
    public ResponseEntity<String> resolveAlert(@PathVariable Long id) {
        alertService.resolveAlert(id);
        return ResponseEntity.ok("Alert resolved");
    }
}

@Data
class AlertRequest {
    public Long userId;
    public String message;
    public double latitude;
    public double longitude;
}
