package com.example.controller;

import com.example.service.OtpService;
import com.example.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public String sendOtp(@RequestParam String phone) {
        // ✅ Step 4: Decode URL-encoded phone number
        String decodedPhone = URLDecoder.decode(phone, StandardCharsets.UTF_8);
        System.out.println("Decoded Phone: " + decodedPhone);

        // Optional: Validate format (ensure +91 or +1, etc.)
        if (!decodedPhone.startsWith("+")) {
            decodedPhone = "+91" + decodedPhone;
            System.out.println("Auto-corrected phone: " + decodedPhone);
        }


        // Generate OTP
        String otp = otpService.generateOtp(decodedPhone);

        // Send SMS via Twilio
        smsService.sendSms(decodedPhone, "Your OTP is: " + otp);

        return "OTP sent to " + decodedPhone;
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String phone, @RequestParam String otp) {
        boolean isValid = otpService.verifyOtp(phone, otp);
        return isValid ? "OTP verified successfully!" : "Invalid or expired OTP.";
    }
}
