package com.example.controller;
import com.example.model.User;
import com.example.model.UserUpdateRequest;
import com.example.repository.UserRepository;
import com.example.repository.UserUpdateRequestRepository;
import java.time.LocalDateTime;
import com.example.service.UserUpdateRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/update-request")
public class UserUpdateRequestController {

    @Autowired
    private UserUpdateRequestService updateRequestService;

    // ✅ Step 1: User submits update request
    @PostMapping("/submit/{userId}")
    public UserUpdateRequest submitRequest(@PathVariable Long userId,
                                           @RequestParam String newName,
                                           @RequestParam String newEmail,
                                           @RequestParam String newPhone) {
        return updateRequestService.submitUpdateRequest(userId, newName, newEmail, newPhone);
    }

    // ✅ Step 2: Admin views pending requests
    @GetMapping("/pending")
    public List<UserUpdateRequest> viewPendingRequests() {
        return updateRequestService.getPendingRequests();
    }

    // ✅ Step 3: Admin approves request
    @PostMapping("/approve/{requestId}")
    public UserUpdateRequest approve(@PathVariable Long requestId) {
        return updateRequestService.approveRequest(requestId);
    }
   
}
