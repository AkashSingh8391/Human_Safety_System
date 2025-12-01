package com.safety.controller;

import com.safety.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestEmailController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/email")
    public String sendTestEmail() {
        notificationService.sendEmail(
                "akashgurjar6266@gmail.com",
                "Test SOS Email",
                "This is a working test email."
        );
        return "Email sent!";
    }
}
