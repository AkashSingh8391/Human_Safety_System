package com.safety.controller;

import com.safety.model.Alert;
import com.safety.model.User;
import com.safety.repository.AlertRepository;
import com.safety.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alert")
public class AlertController {

    @Autowired private AlertRepository alertRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private JavaMailSender mailSender;

    // Create new SOS
    @PostMapping("/sos")
    public Alert sendSos(@RequestBody Alert alert) {
        alert.setTimestamp(Instant.now());
        alert.setResolved(false);
        Alert saved = alertRepo.save(alert);

        // send email to user's email (if userId provided)
        if (saved.getUserId() != null) {
            Optional<User> ou = userRepo.findById(saved.getUserId());
            if (ou.isPresent()) {
                User u = ou.get();
                if (u.getEmail() != null && !u.getEmail().isBlank()) {
                    sendEmail(u.getEmail(), "SOS Alert - HumanSafety", buildEmailText(saved, u));
                }
                // Optional: send SMS via Twilio (commented)
                if (u.getPhoneNo() != null && !u.getPhoneNo().isBlank()) {
                    System.out.println("SMS (demo) to: " + u.getPhoneNo() + " -> " + buildSmsText(saved));
                    // If you want real SMS, integrate Twilio below (see commented block)
                }
            }
        }

        return saved;
    }

    // Update location for existing alert
    @PostMapping("/update/{alertId}")
    public Alert updateLocation(@PathVariable Long alertId, @RequestBody Alert partial) {
        Alert a = alertRepo.findById(alertId).orElseThrow();
        a.setLatitude(partial.getLatitude());
        a.setLongitude(partial.getLongitude());
        a.setTimestamp(Instant.now());
        return alertRepo.save(a);
    }

    @GetMapping("/active")
    public List<Alert> activeAlerts() {
        return alertRepo.findByResolvedFalse();
    }

    @GetMapping("/track/{alertId}")
    public Optional<Alert> track(@PathVariable Long alertId) {
        return alertRepo.findById(alertId);
    }

    // Email helper
    private void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            mailSender.send(msg);
            System.out.println("Email sent to " + to);
        } catch (Exception e) {
            System.err.println("Email send failed: " + e.getMessage());
        }
    }

    private String buildSmsText(Alert a) {
        String maps = "https://www.google.com/maps/search/?api=1&query=" + a.getLatitude() + "," + a.getLongitude();
        return "SOS! " + a.getMessage() + " Location: " + maps;
    }

    private String buildEmailText(Alert a, User u) {
        String maps = "https://www.google.com/maps/search/?api=1&query=" + a.getLatitude() + "," + a.getLongitude();
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ").append(u.getUsername()).append(",\n\n");
        sb.append("An SOS was triggered.\n\n");
        sb.append("Message: ").append(a.getMessage()).append("\n");
        sb.append("Time: ").append(a.getTimestamp()).append("\n");
        sb.append("Location: ").append(maps).append("\n\n");
        sb.append("Regards,\nHumanSafetySystem");
        return sb.toString();
    }

    /*  // Twilio SMS example (UNCOMMENT + configure properties and Twilio dependency)
    @Autowired private Environment env;
    private void sendSms(String to, String body) {
        String sid = env.getProperty("twilio.accountSid");
        String token = env.getProperty("twilio.authToken");
        String from = env.getProperty("twilio.fromNumber");
        Twilio.init(sid, token);
        Message.creator(new PhoneNumber(to), new PhoneNumber(from), body).create();
    }
    */
}
