package com.safety.service;

import com.safety.model.User;
import com.safety.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority; // <<< ADDED THIS IMPORT
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // Note: The User object here is org.springframework.security.core.userdetails.User
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), 
                u.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + u.getRole()))
        );
    }
}