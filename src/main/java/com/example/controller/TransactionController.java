package com.example.controller;

import com.example.model.Transaction;
import com.example.service.TransactionService;
import com.twilio.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Deposit money to user account
    @PostMapping("/deposit/{userId}")
    public ResponseEntity<?> deposit(@PathVariable Long userId, @RequestParam Double amount) {
        try {
            Transaction txn = transactionService.deposit(userId, amount);
            return ResponseEntity.ok(txn);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Deposit failed: " + e.getMessage());
        }
    }

    // Withdraw money from user account
    @PostMapping("/withdraw/{userId}")
    public ResponseEntity<?> withdraw(@PathVariable Long userId, @RequestParam Double amount) {
        try {
            Transaction txn = transactionService.withdraw(userId, amount);
            return ResponseEntity.ok(txn);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Withdrawal failed: " + e.getMessage());
        }
    }

    // Transfer money between two users
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(
            @RequestParam Long fromUserId,
            @RequestParam Long toUserId,
            @RequestParam Double amount) {
        try {
            Transaction txn = transactionService.transfer(fromUserId, toUserId, amount);
            return ResponseEntity.ok(txn);
        } catch (ApiException e) {
            return ResponseEntity.ok("✅ Transfer succeeded " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Transfer failed: " + e.getMessage());
        }
    }

    // Get transaction history for a user
    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getTransactionHistory(@PathVariable Long userId) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
            return ResponseEntity.ok(transactions);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Error retrieving transaction history.");
        }
    }

    // Duplicate endpoint for direct user transactions (used by frontend)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }
}
