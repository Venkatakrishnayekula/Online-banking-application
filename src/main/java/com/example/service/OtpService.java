package com.example.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OtpService {

    private final Map<String, OtpEntry> otpMap = new HashMap<>();

    public String generateOtp(String phone) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        otpMap.put(phone, new OtpEntry(otp, LocalDateTime.now()));
        return otp;
    }


    public boolean verifyOtp(String phone, String inputOtp) {
        OtpEntry entry = otpMap.get(phone);
        if (entry == null) return false;

        boolean isExpired = entry.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now());
        if (isExpired) {
            otpMap.remove(phone);
            return false;
        }

        if (entry.getOtp().equals(inputOtp)) {
            otpMap.remove(phone); // OTP should be single-use
            return true;
        }

        return false;
    }

    // Inner class to store OTP + timestamp
    private static class OtpEntry {
        private final String otp;
        private final LocalDateTime createdAt;

        public OtpEntry(String otp, LocalDateTime createdAt) {
            this.otp = otp;
            this.createdAt = createdAt;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }
}
