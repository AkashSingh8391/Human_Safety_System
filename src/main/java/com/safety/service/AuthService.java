package com.safety.service;

import com.safety.model.User;
import com.safety.repository.UserRepository;
import com.safety.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public String login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            if(encoder.matches(password, user.getPassword())) {
                return jwtTokenProvider.generateToken(user.getUsername(), user.getRole());
            }
        }
        throw new RuntimeException("Invalid username/password");
    }
}
