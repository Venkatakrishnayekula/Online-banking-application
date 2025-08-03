package com.example.controller;

import com.example.dto.LoginRequest;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/approve/{id}")
    public User approveUser(@PathVariable Long id) {
        return userService.approveUser(id);
    }
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(user);
    }

    
    @PostMapping("/request-update")
    public String requestUpdate(@RequestParam Long userId,
                                @RequestParam String newName,
                                @RequestParam String newEmail) {
        return userService.requestProfileUpdate(userId, newName, newEmail);
    }

    @PutMapping("/approve-update")
    public String approveUpdate(@RequestParam Long requestId) {
        return userService.approveUpdateRequest(requestId);
    }
    @GetMapping("/transactions")
    public String showTransactionsPage() {
        return "online-banking-frontend/transactions"; // NO .html extension
    }


}
