package com.zerotouch.EntityScan.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "Wallet")
public class UserWallet extends SuperBean {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WALLET_SEQ")
	@SequenceGenerator(name = "WALLET_SEQ", sequenceName = "WALLET_ID_SEQ", allocationSize = 1, initialValue = 10001)
	private int id;
	
	@Column(name = "Balance", nullable = false)
	private float balance;
	
	@Column(name = "Max_Balance_Limit", nullable = false)
	private float maxBalanceLimit;
	
	@Column(name = "created_on", nullable = false)
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;

	public UserWallet() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public float getMaxBalanceLimit() {
		return maxBalanceLimit;
	}

	public void setMaxBalanceLimit(float maxBalanceLimit) {
		this.maxBalanceLimit = maxBalanceLimit;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}
