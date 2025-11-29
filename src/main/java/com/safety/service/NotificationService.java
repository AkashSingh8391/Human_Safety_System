package com.safety.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;

@Service
public class NotificationService {

    @Autowired private JavaMailSender mailSender;

    @Value("${twilio.accountSid}") private String twilioSid;
    @Value("${twilio.authToken}") private String twilioToken;
    @Value("${twilio.fromNumber}") private String twilioFrom;

    // send email
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(body);
        mailSender.send(msg);
    }

    // send SMS via Twilio
    public void sendSms(String to, String messageBody) {
        try {
            Twilio.init(twilioSid, twilioToken);
            Message.creator(new com.twilio.type.PhoneNumber(to),
                    new com.twilio.type.PhoneNumber(twilioFrom),
                    messageBody).create();
        } catch (Exception e) {
            System.err.println("SMS error: " + e.getMessage());
        }
    }
}
