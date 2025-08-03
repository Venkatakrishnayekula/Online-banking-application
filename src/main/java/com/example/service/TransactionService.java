package com.example.service;

import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final SmsService smsService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              SmsService smsService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.smsService = smsService;
    }

    // ✅ Deposit
    public Transaction deposit(Long userId, Double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBalance(user.getBalance() == null ? amount : user.getBalance() + amount);
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setType("DEPOSIT");
        transaction.setDate(LocalDateTime.now());

        // SMS
        smsService.sendSms(user.getPhone(),
                "₹" + amount + " deposited successfully. New balance: ₹" + user.getBalance());

        return transactionRepository.save(transaction);
    }

    // ✅ Withdraw
    public Transaction withdraw(Long userId, Double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getBalance() == null || user.getBalance() - amount < 500) {
            throw new RuntimeException("Cannot withdraw. Minimum balance of ₹500 must be maintained.");
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setType("WITHDRAW");
        transaction.setDate(LocalDateTime.now());

        // SMS
        smsService.sendSms(user.getPhone(),
                "₹" + amount + " withdrawn successfully. New balance: ₹" + user.getBalance());

        return transactionRepository.save(transaction);
    }

    // ✅ Transfer
    public Transaction transfer(Long fromUserId, Long toUserId, Double amount) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (fromUser.getBalance() == null || fromUser.getBalance() - amount < 500) {
            throw new RuntimeException("Insufficient balance. Minimum ₹500 must be maintained.");
        }

        toUser.setBalance(toUser.getBalance() == null ? amount : toUser.getBalance() + amount);
        fromUser.setBalance(fromUser.getBalance() - amount);

        userRepository.save(fromUser);
        userRepository.save(toUser);

        Transaction transaction = new Transaction();
        transaction.setUser(fromUser);
        transaction.setAmount(amount);
        transaction.setType("TRANSFER");
        transaction.setDate(LocalDateTime.now());

        // SMS to both users
        smsService.sendSms(fromUser.getPhone(),
                "₹" + amount + " transferred to User ID " + toUserId + ". Remaining balance: ₹" + fromUser.getBalance());

        smsService.sendSms(toUser.getPhone(),
                "₹" + amount + " received from User ID " + fromUserId + ". New balance: ₹" + toUser.getBalance());

        return transactionRepository.save(transaction);
    }

    // ✅ View transaction history
    public List<Transaction> getTransactionsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findByUser(user);
    }
}
