package com.zerotouch.EntityScan.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "Transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSACTION_SEQ")
	@SequenceGenerator(name = "TRANSACTION_SEQ", sequenceName = "TRANSACTION_SEQ", allocationSize = 1, initialValue = 10001)
	private int id;
	@Column(name = "user_id", nullable = false)
	private int userId;
	@Column(name = "tran_ref_no", nullable = false)
	private String transactionRefNumber;
	@Column(name = "Trans_Type", nullable = false)
	private Character transactionType;
	@Column(name = "Trans_Amt", nullable = false)
	private float transactionAmount;
	@Column(name = "merchant_id", nullable = false)
	private int merchantId;
	@Column(name = "comments")
	private String comment;
	@Column(name = "created_on", nullable = false)
	private Date createdOn;
	@Column(name = "updated_on")
	private Date updatedOn;
	
	public Transaction() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTransactionRefNumber() {
		return transactionRefNumber;
	}
	public void setTransactionRefNumber(String transactionRefNumber) {
		this.transactionRefNumber = transactionRefNumber;
	}
	public Character getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Character transactionType) {
		this.transactionType = transactionType;
	}
	public float getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
