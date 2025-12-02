package com.safety.controller;

import com.safety.model.Alert;
import com.safety.model.EmergencyContact;
import com.safety.repository.AlertRepository;
import com.safety.repository.EmergencyContactRepository;
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
    @Autowired private EmergencyContactRepository contactRepo;
    @Autowired private NotificationService notif;
    @Autowired private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/sos")
    public Alert sendSos(@RequestBody Alert alert) {
        System.out.println("SOS RECEIVED userId=" + alert.getUserId());
        alert.setTimestamp(Instant.now());
        alert.setResolved(false);
        Alert saved = alertRepo.save(alert);

        // Broadcast for live tracking
        messagingTemplate.convertAndSend("/topic/alerts", saved);
        messagingTemplate.convertAndSend("/topic/alert/" + saved.getAlertId(), saved);

        // Build maps/link once
        String mapsUrl = "http://localhost:5173/track?lat=" + alert.getLatitude() + "&lng=" + alert.getLongitude();
        String mailBody = "SOS ALERT!\n\nMessage: " + alert.getMessage()
                + "\nLocation: " + mapsUrl + "\nTime: " + saved.getTimestamp();

        // fetch all emergency contacts for this user
        List<EmergencyContact> contacts = contactRepo.findByUserId(alert.getUserId());
        for (EmergencyContact c : contacts) {
            if (c.getEmail() != null && !c.getEmail().isBlank()) {
                try {
                    notif.sendEmail(c.getEmail(), "SOS Alert — Emergency Contact", mailBody);
                    System.out.println("Email sent to: " + c.getEmail());
                } catch (Exception e) {
                    System.err.println("Email error for " + c.getEmail() + " : " + e.getMessage());
                }
            }
            if (c.getPhone() != null && !c.getPhone().isBlank()) {
                try {
                    notif.sendSms(c.getPhone(), "SOS! " + alert.getMessage() + " Location: " + mapsUrl);
                    System.out.println("SMS sent to: " + c.getPhone());
                } catch (Exception e) {
                    System.err.println("SMS error for " + c.getPhone() + " : " + e.getMessage());
                }
            }
        }
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
    public List<Alert> activeAlerts() { return alertRepo.findByResolvedFalse(); }
}
