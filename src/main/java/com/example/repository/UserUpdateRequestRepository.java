package com.example.repository;


import com.example.model.UserUpdateRequest;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserUpdateRequestRepository extends JpaRepository<UserUpdateRequest, Long> {
    List<UserUpdateRequest> findByStatus(String status);
    List<UserUpdateRequest> findByUser(User user);
   // List<UserUpdateRequest> findByApprovedFalse();
    
   
}
