package com.example.service;

import com.example.model.ProfileUpdateRequest;
import com.example.model.User;
import com.example.repository.ProfileUpdateRequestRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProfileUpdateService {

    @Autowired
    private ProfileUpdateRequestRepository requestRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SmsService smsService;

    public String submitRequest(ProfileUpdateRequest request) {
        request.setStatus("PENDING");
        request.setRequestDate(LocalDateTime.now());
        requestRepo.save(request);
        return "Profile update request submitted.";
    }

    public List<ProfileUpdateRequest> getPendingRequests() {
        return requestRepo.findByStatus("PENDING");
    }

    public String approveRequest(Long requestId) {
        ProfileUpdateRequest req = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getNewAddress() != null) user.setAddress(req.getNewAddress());
        if (req.getNewPhone() != null) user.setPhone(req.getNewPhone());
        if (req.getNewEmail() != null) user.setEmail(req.getNewEmail());

        userRepo.save(user);
        req.setStatus("APPROVED");
        requestRepo.save(req);

        smsService.sendSms(user.getPhone(), "Your profile update request has been approved.");

        return "Profile update approved.";
    }

    public String rejectRequest(Long requestId) {
        ProfileUpdateRequest req = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        req.setStatus("REJECTED");
        requestRepo.save(req);
        return "Profile update rejected.";
    }
}
