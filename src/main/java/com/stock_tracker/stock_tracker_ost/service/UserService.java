package com.stock_tracker.stock_tracker_ost.service;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.UserDTO;
import com.stock_tracker.stock_tracker_ost.exceptions.UserNotFoundException;
import com.stock_tracker.stock_tracker_ost.model.User;
import com.stock_tracker.stock_tracker_ost.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private static final List<String> AVAILABLE_ROLES = Arrays.asList("ADMIN", "WORKER", "USER");

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Użytkownik już istnieje");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        User saved = userRepository.save(user);
        return toDTO(saved);
    }

    public UserDTO login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Nieprawidłowe hasło");
        }

        return toDTO(user);
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(this::toDTO).toList();
    }

    public UserDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return toDTO(user);
    }

    public UserDTO update(Long id, String username) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.getUsername().equals(username)) {
            if (userRepository.findByUsername(username).isPresent()) {
                throw new RuntimeException("Nazwa użytkownika jest już zajęta");
            }
            user.setUsername(username);
        }

        return toDTO(userRepository.save(user));
    }

    public UserDTO changeRole(Long id, String role) {
        if (!AVAILABLE_ROLES.contains(role.toUpperCase())) {
            throw new RuntimeException("Nieprawidłowa rola. Dostępne role: " + AVAILABLE_ROLES);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setRole(role.toUpperCase());
        return toDTO(userRepository.save(user));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    public List<String> getAvailableRoles() {
        return AVAILABLE_ROLES;
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getRole());
    }
}
