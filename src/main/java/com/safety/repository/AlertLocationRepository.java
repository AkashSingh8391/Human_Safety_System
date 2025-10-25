package com.safety.repository;


import com.safety.model.AlertLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AlertLocationRepository extends JpaRepository<AlertLocation, Long> {
List<AlertLocation> findByAlertAlertIdOrderByTimestamp(Long alertId);
}