package com.safety.controller;

import com.safety.model.Alert;
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

        System.out.println("\n========================");
        System.out.println("🔔 SOS RECEIVED");
        System.out.println("userId sent = " + alert.getUserId());
        System.out.println("message = " + alert.getMessage());
        System.out.println("lat = " + alert.getLatitude());
        System.out.println("lng = " + alert.getLongitude());
        System.out.println("========================\n");

        alert.setTimestamp(Instant.now());
        alert.setResolved(false);
        Alert saved = alertRepo.save(alert);

        // Broadcast
        messagingTemplate.convertAndSend("/topic/alerts", saved);
        messagingTemplate.convertAndSend("/topic/alert/" + saved.getAlertId(), saved);

        System.out.println("🔍 Finding user inside DB...");

        userRepo.findById(alert.getUserId()).ifPresentOrElse(user -> {

            System.out.println("✔ USER FOUND: " + user.getUsername());
            System.out.println("✔ Emergency Email: " + user.getEmergencyEmail());
            System.out.println("✔ Emergency Phone: " + user.getEmergencyPhone());

            String mapsUrl = "https://www.google.com/maps?q=" + alert.getLatitude() + "," + alert.getLongitude();
            String mailBody = "SOS ALERT!\n\nMessage: " + alert.getMessage() +
                    "\nLocation: " + mapsUrl +
                    "\nTime: " + saved.getTimestamp();

            try {
                System.out.println("📧 Sending SOS email to " + user.getEmergencyEmail());
                notif.sendEmail(user.getEmergencyEmail(), "SOS Alert — Your Contact", mailBody);
                System.out.println("📧 Email send attempted!");
            } catch (Exception e) {
                System.out.println("❌ Email failed: " + e.getMessage());
                e.printStackTrace();
            }

        }, () -> {
            System.out.println("❌ USER NOT FOUND IN DB for userId = " + alert.getUserId());
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
