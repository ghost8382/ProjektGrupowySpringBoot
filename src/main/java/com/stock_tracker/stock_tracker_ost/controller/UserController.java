package com.stock_tracker.stock_tracker_ost.controller;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.LoginRequest;
import com.stock_tracker.stock_tracker_ost.DataTransferObject.UserDTO;
import com.stock_tracker.stock_tracker_ost.model.User;
import com.stock_tracker.stock_tracker_ost.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginRequest request) {
        return userService.login(request.getUsername(), request.getPassword());
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAll();
    }
}