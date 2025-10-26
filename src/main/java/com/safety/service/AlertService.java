package com.safety.service;

import com.safety.model.Alert;
import com.safety.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public Alert createAlert(Alert alert) {
        alert.setTimestamp(LocalDateTime.now());
        alert.setResolved(false);
        return alertRepository.save(alert);
    }

    public List<Alert> getActiveAlerts() {
        return alertRepository.findByResolvedFalse();
    }

    public void resolveAlert(Long id) {
        Alert alert = alertRepository.findById(id).orElseThrow();
        alert.setResolved(true);
        alertRepository.save(alert);
    }
}
