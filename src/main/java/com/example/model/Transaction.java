package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // DEPOSIT or WITHDRAW
    private Double amount;
    private LocalDateTime timestamp;
    private LocalDateTime date;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Transaction() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public LocalDateTime getDate() {
	    return date;
	}

	public void setDate(LocalDateTime date) {
	    this.date = date;
	}

	
    // constructor, getters, setters
}
