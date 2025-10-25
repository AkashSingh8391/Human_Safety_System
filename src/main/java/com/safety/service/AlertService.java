package com.safety.service;

import com.safety.model.Alert;
import com.safety.model.User;
import com.safety.repository.AlertRepository;
import com.safety.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private UserRepository userRepository;

    public Alert createAlert(Long userId, String message, double latitude, double longitude) {
        User user = userRepository.findById(userId).orElseThrow();
        Alert alert = new Alert();
        alert.setUser(user);
        alert.setMessage(message);
        alert.setLatitude(latitude);
        alert.setLongitude(longitude);
        alert.setTimestamp(LocalDateTime.now());
        alert.setResolved(false);
        return alertRepository.save(alert);
    }

    public List<Alert> getActiveAlerts() {
        return alertRepository.findByResolvedFalse();
    }

    public void resolveAlert(Long alertId) {
        Alert alert = alertRepository.findById(alertId).orElseThrow();
        alert.setResolved(true);
        alertRepository.save(alert);
    }
}
