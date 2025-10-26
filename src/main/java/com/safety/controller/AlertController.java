package com.safety.controller;

import com.safety.model.Alert;
import com.safety.service.AlertService;
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
    public ResponseEntity<Alert> sendSOS(@RequestBody Alert alert){
        return ResponseEntity.ok(alertService.createAlert(alert));
    }

    @GetMapping("/police")
    public ResponseEntity<List<Alert>> getActiveAlerts(){
        return ResponseEntity.ok(alertService.getActiveAlerts());
    }

    @PutMapping("/resolve/{id}")
    public ResponseEntity<String> resolveAlert(@PathVariable Long id){
        alertService.resolveAlert(id);
        return ResponseEntity.ok("Alert resolved");
    }
}
