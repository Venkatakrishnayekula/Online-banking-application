package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.ProfileUpdateRequest;
import com.example.service.ProfileUpdateService;

@RestController
@RequestMapping("/profile-update")
public class ProfileUpdateController {

    @Autowired
    private ProfileUpdateService updateService;

    // User submits update request
    @PostMapping("/request")
    public ResponseEntity<String> submit(@RequestBody ProfileUpdateRequest request) {
        return ResponseEntity.ok(updateService.submitRequest(request));
    }

    // Admin: Get all pending requests
    @GetMapping("/pending")
    public List<ProfileUpdateRequest> pendingRequests() {
        return updateService.getPendingRequests();
    }

    // Admin: Approve
    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        return ResponseEntity.ok(updateService.approveRequest(id));
    }

    // Admin: Reject
    @PutMapping("/reject/{id}")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        return ResponseEntity.ok(updateService.rejectRequest(id));
    }
}
