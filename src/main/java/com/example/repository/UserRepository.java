package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // To find unapproved users
    List<User> findByApprovedFalse();

    // To login only approved users
    Optional<User> findByEmailAndPasswordAndApprovedTrue(String email, String password);

    // For Admin: show only users with pending approval (inactive & unapproved)
    List<User> findByApprovedFalseAndStatus(String status);
}
