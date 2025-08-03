	package com.example.service;
	
	
	
	
	import com.twilio.Twilio;
	import com.twilio.rest.api.v2010.account.Message;
	import com.twilio.type.PhoneNumber;
	
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.stereotype.Service;
	
	@Service
	public class SmsService {
	
	    @Value("${twilio.accountSid}")
	    private String accountSid;
	
	    @Value("${twilio.authToken}")
	    private String authToken;
	
	    @Value("${twilio.phoneNumber}")
	    private String fromNumber;
	
	
	public void sendSms(String toPhoneNumber, String message) {
	    Twilio.init(accountSid, authToken); // ✅ Initialize here
	
	    System.out.println("Sending to: " + toPhoneNumber);
	    
	
	    Message.creator(
	        new PhoneNumber(toPhoneNumber),       // Destination phone
	        new PhoneNumber(fromNumber),          // Your Twilio trial number
	        message
	    ).create();
	}
	}
