package com.example.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
	public class ProfileUpdateRequest {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long userId;
	    private String newAddress;
	    private String newPhone;
	    private String newEmail;
	    private String status = "PENDING"; // PENDING, APPROVED, REJECTED

	    private LocalDateTime requestDate = LocalDateTime.now();

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getNewAddress() {
			return newAddress;
		}

		public void setNewAddress(String newAddress) {
			this.newAddress = newAddress;
		}

		public String getNewPhone() {
			return newPhone;
		}

		public void setNewPhone(String newPhone) {
			this.newPhone = newPhone;
		}

		public String getNewEmail() {
			return newEmail;
		}

		public void setNewEmail(String newEmail) {
			this.newEmail = newEmail;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public LocalDateTime getRequestDate() {
			return requestDate;
		}

		public void setRequestDate(LocalDateTime requestDate) {
			this.requestDate = requestDate;
		}

	}


