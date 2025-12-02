package com.safety.controller;

import com.safety.model.Alert;
import com.safety.model.EmergencyContact;
import com.safety.repository.AlertRepository;
import com.safety.repository.EmergencyContactRepository;
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
    @Autowired private EmergencyContactRepository contactRepo;
    @Autowired private NotificationService notif;
    @Autowired private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/sos")
    public Alert sendSos(@RequestBody Alert alert) {

        alert.setTimestamp(Instant.now());
        alert.setResolved(false);
        Alert saved = alertRepo.save(alert);

        // Fetch ALL emergency contacts of this user
        List<EmergencyContact> contacts = contactRepo.findByUserId(alert.getUserId());

        String mapsUrl = "https://www.google.com/maps?q=" + alert.getLatitude() + "," + alert.getLongitude();
        String mailBody = "🚨 SOS ALERT!\n\nMessage: " + alert.getMessage() +
                "\nLocation: " + mapsUrl +
                "\nTime: " + saved.getTimestamp();

        // Send email + SMS to all contacts
        for (EmergencyContact c : contacts) {

            if (c.getEmail() != null && !c.getEmail().isBlank()) {
                notif.sendEmail(c.getEmail(), "SOS Alert", mailBody);
            }

            if (c.getPhone() != null && !c.getPhone().isBlank()) {
                notif.sendSms(c.getPhone(), "SOS! " + alert.getMessage() + " Location: " + mapsUrl);
            }
        }

        // WebSocket broadcast (Optional)
        messagingTemplate.convertAndSend("/topic/alerts", saved);

        return saved;
    }

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
    public List<Alert> activeAlerts() {
        return alertRepo.findByResolvedFalse();
    }
}
