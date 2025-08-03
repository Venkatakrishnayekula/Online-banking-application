package com.example.controller;

import com.example.model.User;
import com.example.model.UserUpdateRequest;
import com.example.model.Transaction;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import com.example.repository.UserUpdateRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserUpdateRequestRepository updateRequestRepo;

    // ✅ 1. Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/admin-dashboard")
    public String showAdminDashboard() {
        return "online-banking-frontend/admin-dashboard"; 
    }


    // ✅ 2. Get pending users (not approved & inactive)
    @GetMapping("/pending-users")
    public ResponseEntity<List<User>> getPendingUsers() {
        List<User> pending = userRepository.findByApprovedFalseAndStatus("INACTIVE");
        return ResponseEntity.ok(pending);
    }

    // ✅ 3. Approve a user
    @PutMapping("/approve-user/{id}")
    public ResponseEntity<String> approveUser(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(user -> {
                user.setApproved(true);
                user.setStatus("ACTIVE");
                userRepository.save(user);
                return ResponseEntity.ok("✅ User approved successfully.");
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ User not found."));
    }

    // ✅ 4. Delete a user
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ User not found.");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("🗑️ User deleted.");
    }

    // ✅ 5. Get pending profile update requests
    @GetMapping("/profile-update-requests")
    public ResponseEntity<List<UserUpdateRequest>> getPendingProfileRequests() {
        List<UserUpdateRequest> requests = updateRequestRepo.findByStatus("PENDING");
        return ResponseEntity.ok(requests);
    }

    // ✅ 6. Get all transactions
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> txns = transactionRepository.findAll();
        return ResponseEntity.ok(txns);
    }
 // ✅ 7. Approve a profile update request
    @PutMapping("/approve-profile-update/{requestId}")
    public ResponseEntity<String> approveProfileUpdate(@PathVariable Long requestId) {
        UserUpdateRequest request = updateRequestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User user = request.getUser();
        user.setFullName(request.getNewName());
        user.setEmail(request.getNewEmail());

        request.setStatus("APPROVED");

        userRepository.save(user);
        updateRequestRepo.save(request);

        return ResponseEntity.ok("✅ Profile update approved and applied.");
    }
   
    }


