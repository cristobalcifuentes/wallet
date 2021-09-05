package com.alkemy.java.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="foreign_exchange")
public class ForeignExchange {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_foreign_exchange")
    private Integer id;

	@Column(name = "balance")
    private Double balance;
    
    @Column (name = "active")
    private Boolean active;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "foreign_exchange_user_fk"))
    private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
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

	@Override
	public String toString() {
		return "ForeignExchange [id=" + id + ", balance=" + balance + ", active=" + active + ", user=" + user + "]";
	}

	public ForeignExchange(User user) {
		
		this.balance = 0.0;
		this.active = true;
		this.user = user;
	}

	public ForeignExchange() {
	}
	
	
    
    
    

}
