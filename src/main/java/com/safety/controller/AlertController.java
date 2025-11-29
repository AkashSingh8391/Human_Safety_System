package com.safety.controller;

import com.safety.model.Alert;
import com.safety.model.User;
import com.safety.repository.AlertRepository;
import com.safety.repository.UserRepository;
import com.safety.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/alert")
public class AlertController {

    @Autowired private AlertRepository alertRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private NotificationService notif;
    @Autowired private SimpMessagingTemplate messagingTemplate;

    // create SOS (civil will call)
    @PostMapping("/sos")
    public Alert sendSos(@RequestBody Alert alert) {
        alert.setTimestamp(Instant.now());
        alert.setResolved(false);
        Alert saved = alertRepo.save(alert);

        // Broadcast via WebSocket for live tracking
        messagingTemplate.convertAndSend("/topic/alerts", saved);
        messagingTemplate.convertAndSend("/topic/alert/" + saved.getAlertId(), saved);

        // Send Email + SMS to user's saved emergency contact
        userRepo.findById(alert.getUserId()).ifPresent(user -> {
            String mapsUrl = "https://www.google.com/maps?q=" + alert.getLatitude() + "," + alert.getLongitude();
            String mailBody = "SOS ALERT!\n\nMessage: " + alert.getMessage() + "\nLocation: " + mapsUrl + "\nTime: " + saved.getTimestamp();
            try { notif.sendEmail(user.getEmergencyEmail(), "SOS Alert — Your Contact", mailBody); } catch (Exception e) { e.printStackTrace(); }

            try { notif.sendSms(user.getEmergencyPhone(), "SOS! " + alert.getMessage() + " Location: " + mapsUrl); } catch (Exception e) { e.printStackTrace(); }
        });

        return saved;
    }

    // update location for an alert (civil client will call)
    @PostMapping("/update/{alertId}")
    public Alert updateLocation(@PathVariable Long alertId, @RequestBody Alert partial) {
        Alert a = alertRepo.findById(alertId).orElseThrow();
        a.setLatitude(partial.getLatitude());
        a.setLongitude(partial.getLongitude());
        a.setTimestamp(Instant.now());
        Alert saved = alertRepo.save(a);

        messagingTemplate.convertAndSend("/topic/alert/" + alertId, saved);
        messagingTemplate.convertAndSend("/topic/alerts", saved);
        return saved;
    }

    @GetMapping("/active")
    public List<Alert> activeAlerts() { return alertRepo.findByResolvedFalse(); }
}
