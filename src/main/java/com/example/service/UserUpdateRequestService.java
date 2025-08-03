package com.example.service;	

	import com.example.model.User;
	import com.example.model.UserUpdateRequest;
	import com.example.repository.UserRepository;
	import com.example.repository.UserUpdateRequestRepository;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

	import java.time.LocalDateTime;
	import java.util.List;

	@Service
	public class UserUpdateRequestService {

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private UserUpdateRequestRepository requestRepository;

	    // ✅ Step 1: User submits update request
	    public UserUpdateRequest requestUpdate(Long userId, String newName, String newEmail, String newPhone) {
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        UserUpdateRequest request = new UserUpdateRequest();
	        request.setUser(user);
	        request.setNewName(newName);
	        request.setNewEmail(newEmail);
	        request.setNewPhone(newPhone);
	        request.setStatus("PENDING");
	        request.setRequestedAt(LocalDateTime.now());

	        return requestRepository.save(request);
	    }

	    // ✅ Step 2: Admin views all pending requests
	    public List<UserUpdateRequest> getPendingRequests() {
	        return requestRepository.findByStatus("PENDING");
	    }

	    // ✅ Step 3: Admin approves a request
	    public UserUpdateRequest approveRequest(Long requestId) {
	        UserUpdateRequest request = requestRepository.findById(requestId)
	                .orElseThrow(() -> new RuntimeException("Request not found"));

	        User user = request.getUser();
	        if (request.getNewName() != null) user.setFullName(request.getNewName());
	        if (request.getNewEmail() != null) user.setEmail(request.getNewEmail());
	        if (request.getNewPhone() != null) user.setPhone(request.getNewPhone());

	        userRepository.save(user);

	        request.setStatus("APPROVED");
	        return requestRepository.save(request);
	    }

	    // ✅ Optional: Admin rejects a request
	    public UserUpdateRequest rejectRequest(Long requestId) {
	        UserUpdateRequest request = requestRepository.findById(requestId)
	                .orElseThrow(() -> new RuntimeException("Request not found"));

	        request.setStatus("REJECTED");
	        return requestRepository.save(request);
	    }
	    //submit update request
	    public UserUpdateRequest submitUpdateRequest(Long userId, String newName, String newEmail, String newPhone) {
	        User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	        UserUpdateRequest request = new UserUpdateRequest();
	        request.setUser(user);
	        request.setNewName(newName);
	        request.setNewEmail(newEmail);
	        request.setNewPhone(newPhone);
	        request.setStatus("PENDING");
	        request.setRequestedAt(LocalDateTime.now());

	        return requestRepository.save(request);
	    }

	}

