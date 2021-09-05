package com.alkemy.java.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;
	
	@Column(name = "date", nullable = false)
	private LocalDateTime date;
	
	@NotNull
	@Column(name = "amount", nullable = false)
	private Double amount;
	
	@NotNull
	@Column(name = "detail", nullable = false)
	private String detail;
	
	//@NotNull
	@Column(name = "status")
	private String status;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "transaction_user_fk"))
	private User user;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Transaction() {
	}

	public Transaction(@NotNull LocalDateTime date, @NotNull Double amount,
			@NotNull String detail, @NotNull String status, @NotNull User user, TransactionType transactionType) {
		this.date = date;
		this.amount = amount;
		this.detail = detail;
		this.status = status;
		this.user = user;
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", date=" + date + ", amount=" + amount + ", detail="
				+ detail + ", status=" + status + ", user=" + user + "]";
	}
	
	
	
}
