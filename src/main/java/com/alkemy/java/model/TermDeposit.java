package com.alkemy.java.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "term_deposit")
@SQLDelete(sql = "UPDATE term_deposit SET active=false WHERE id=?")
public class TermDeposit {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Integer id;
    
    @Column(name="start_date", nullable = false)
    private LocalDateTime startDate;
    
    @Column(name = "withdrawal_date", nullable = true)
    private LocalDateTime withdrawalDate;

    @Column(name = "deposit_amount", nullable = false)
    private Double depositAmount;

    @Column (name = "active", nullable = false)
    private Boolean active;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "term_deposit_user_fk"))
    private User user;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
    
    @PrePersist
    public void prePersist() {
    	startDate = LocalDateTime.now();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getWithdrawalDate() {
		return withdrawalDate;
	}

	public void setWithdrawalDate(LocalDateTime withdrawalDate) {
		this.withdrawalDate = withdrawalDate;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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

	public TermDeposit() {
	}

	public TermDeposit(Integer id, LocalDateTime startDate, LocalDateTime withdrawalDate, Double depositAmount, Boolean active, User user,
			TransactionType transactionType) {
		this.id = id;
		this.startDate = startDate;
		this.withdrawalDate = withdrawalDate;
		this.depositAmount = depositAmount;
		this.active = active;
		this.user = user;
		this.transactionType = transactionType;
	}


}
