package com.safety.controller;

import com.safety.model.Alert;
import com.safety.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alert")
public class AlertController {

    @Autowired
    private AlertRepository alertRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Civil sends SOS
    @PostMapping("/sos")
    public Alert sendSos(@RequestBody Alert alert) {
        alert.setTimestamp(Instant.now());
        alert.setResolved(false);
        Alert saved = alertRepo.save(alert);

        // broadcast new active alert to police clients
        messagingTemplate.convertAndSend("/topic/alerts", saved);

        // also send specific alert channel for trackers
        messagingTemplate.convertAndSend("/topic/alert/" + saved.getAlertId(), saved);

        return saved;
    }

    // Police gets all active alerts
    @GetMapping("/active")
    public List<Alert> activeAlerts() {
        return alertRepo.findByResolvedFalse();
    }

    // Get last location for an alert (for track)
    @GetMapping("/track/{alertId}")
    public Optional<Alert> track(@PathVariable Long alertId) {
        return alertRepo.findById(alertId);
    }

    @PutMapping("/resolve/{id}")
    public String resolve(@PathVariable Long id) {
        Optional<Alert> opt = alertRepo.findById(id);
        if (opt.isPresent()) {
            Alert a = opt.get();
            a.setResolved(true);
            alertRepo.save(a);
            // notify removal or resolved state
            messagingTemplate.convertAndSend("/topic/alerts", a);
            messagingTemplate.convertAndSend("/topic/alert/" + id, a);
            return "Resolved";
        } else return "NotFound";
    }

    // For demo: save new coordinate for existing alert (simulate movement)
    @PostMapping("/update/{alertId}")
    public Alert updateLocation(@PathVariable Long alertId, @RequestBody Alert partial) {
        Alert a = alertRepo.findById(alertId).orElseThrow();
        a.setLatitude(partial.getLatitude());
        a.setLongitude(partial.getLongitude());
        a.setTimestamp(Instant.now());
        Alert saved = alertRepo.save(a);

        // broadcast updated coordinates to listeners
        messagingTemplate.convertAndSend("/topic/alert/" + alertId, saved);

        // optionally broadcast to general alerts topic
        messagingTemplate.convertAndSend("/topic/alerts", saved);

        return saved;
    }
}
