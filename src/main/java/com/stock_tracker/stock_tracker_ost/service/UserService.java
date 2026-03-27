package com.stock_tracker.stock_tracker_ost.service;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.UserDTO;
import com.stock_tracker.stock_tracker_ost.model.User;
import com.stock_tracker.stock_tracker_ost.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO register(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        User saved = userRepository.save(user);

        return new UserDTO(
                saved.getId(),
                saved.getUsername(),
                saved.getRole()
        );
    }

    public UserDTO login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserDTO(u.getId(), u.getUsername(), u.getRole()))
                .toList();
    }
}