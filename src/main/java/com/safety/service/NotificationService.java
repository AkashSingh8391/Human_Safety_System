package com.safety.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class NotificationService {

    @Autowired private JavaMailSender mailSender;

    @Value("${spring.mail.from}") private String fromEmail;
    @Value("${twilio.accountSid}") private String twilioSid;
    @Value("${twilio.authToken}") private String twilioToken;
    @Value("${twilio.fromNumber}") private String twilioFrom;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(fromEmail);
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            mailSender.send(msg);
            System.out.println("EMAIL SENT TO: " + to);
        } catch (Exception e) {
            System.out.println("EMAIL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendSms(String to, String messageBody) {
        try {
            if (twilioSid == null || twilioSid.isBlank() || twilioToken == null || twilioToken.isBlank()) {
                System.err.println("Twilio credentials missing — SMS not sent to " + to);
                return;
            }
            Twilio.init(twilioSid, twilioToken);
            Message.creator(new com.twilio.type.PhoneNumber(to),
                    new com.twilio.type.PhoneNumber(twilioFrom),
                    messageBody).create();
            System.out.println("SMS SENT TO: " + to);
        } catch (Exception e) {
            System.err.println("SMS error: " + e.getMessage());
        }
    }
}
