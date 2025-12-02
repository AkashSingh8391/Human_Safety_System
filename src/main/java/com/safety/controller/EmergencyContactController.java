package com.safety.controller;

import com.safety.model.EmergencyContact;
import com.safety.repository.EmergencyContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class EmergencyContactController {

    @Autowired
    private EmergencyContactRepository contactRepo;

    @PostMapping("/add")
    public EmergencyContact addContact(@RequestBody EmergencyContact contact) {
        return contactRepo.save(contact);
    }

    @GetMapping("/user/{userId}")
    public List<EmergencyContact> getByUser(@PathVariable Long userId){
        return contactRepo.findByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id){
        contactRepo.deleteById(id);
    }
}
