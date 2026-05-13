package com.stock_tracker.stock_tracker_ost.controller;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.LoginRequest;
import com.stock_tracker.stock_tracker_ost.DataTransferObject.UserDTO;
import com.stock_tracker.stock_tracker_ost.model.User;
import com.stock_tracker.stock_tracker_ost.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginRequest request) {
        return userService.login(request.getUsername(), request.getPassword());
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Long id, @RequestParam String username) {
        return userService.update(id, username);
    }

    @PutMapping("/{id}/role")
    public UserDTO changeRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return userService.changeRole(id, body.get("role"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/roles")
    public List<String> getRoles() {
        return userService.getAvailableRoles();
    }
}
