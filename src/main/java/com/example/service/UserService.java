package com.example.service;

import com.example.model.User;
import com.example.model.UserUpdateRequest;
import com.example.repository.UserRepository;
import com.example.repository.UserUpdateRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUpdateRequestRepository updateRequestRepo;

    @Autowired
    private SmsService smsService;

    // 1. Register user (defaults to inactive + unapproved)
    public User registerUser(User user) {
        user.setRole("USER");
        user.setApproved(false);
        user.setStatus("INACTIVE");
        return userRepository.save(user);
    }

    // 2. Get all users (for admin)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 3. Get pending approval users
    public List<User> getPendingApprovalUsers() {
        return userRepository.findByApprovedFalseAndStatus("INACTIVE");
    }

    // 4. Approve user
    public User approveUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setApproved(true);
        user.setStatus("ACTIVE");

        smsService.sendSms(user.getPhone(), "Hi " + user.getFullName() + ", your account has been approved!");
        return userRepository.save(user);
    }

    // 5. Login
    public User login(String email, String password) {
        return userRepository.findByEmailAndPasswordAndApprovedTrue(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid credentials or user not approved"));
    }

    // 6. User requests profile update
    public String requestProfileUpdate(Long userId, String newName, String newEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserUpdateRequest request = new UserUpdateRequest();
        request.setUser(user);
        request.setNewName(newName);
        request.setNewEmail(newEmail);
        request.setStatus("PENDING");
        request.setRequestedAt(java.time.LocalDateTime.now());

        updateRequestRepo.save(request);
        return "Update request submitted for approval!";
    }

    // 7. Admin approves profile update request
    public String approveUpdateRequest(Long requestId) {
        UserUpdateRequest req = updateRequestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User user = req.getUser();
        if (req.getNewName() != null) user.setFullName(req.getNewName());
        if (req.getNewEmail() != null) user.setEmail(req.getNewEmail());
        if (req.getNewPhone() != null) user.setPhone(req.getNewPhone());

        userRepository.save(user);

        req.setStatus("APPROVED");
        updateRequestRepo.save(req);

        smsService.sendSms(user.getPhone(), "Your profile update request has been approved.");
        return "Update approved and applied!";
    }
}
