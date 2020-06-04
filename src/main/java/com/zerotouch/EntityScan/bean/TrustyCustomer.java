package com.zerotouch.EntityScan.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "Trusty_Customer")
public class TrustyCustomer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRUSTYCUSTOMER_SEQ")
	@SequenceGenerator(name = "TRUSTYCUSTOMER_SEQ", sequenceName = "TRUSTYCUSTOMER_SEQ", allocationSize = 1, initialValue = 10001)
	@Column(name="id")
	private int id;
	@Column(name = "cust_id")
	private int userId;
	@Column(name = "mcht_id")
	private int mrcntId;
	@Column(name = "Trusted_Flag")
	private Character trustedFlag;
	@Column(name = "Trans_Amount_Limit")
	private float txnAmountLimit;
	@Column(name = "No_Trans_Limit")
	private int txnNumberLimit;
	@Column(name = "Max_Amt_PerDayLimit")
	private float txnAmountPerDayLimit;
	@Column(name = "created_on", nullable = false)
	private Date createdOn;
	@Column(name = "updated_on")
	private Date updatedOn;
	
	public TrustyCustomer() {
		
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
	public int getMrcntId() {
		return mrcntId;
	}
	public void setMrcntId(int mrcntId) {
		this.mrcntId = mrcntId;
	}
	public Character getTrustedFlag() {
		return trustedFlag;
	}
	public void setTrustedFlag(Character trustedFlag) {
		this.trustedFlag = trustedFlag;
	}
	public float getTxnAmountLimit() {
		return txnAmountLimit;
	}
	public void setTxnAmountLimit(int txnAmountLimit) {
		this.txnAmountLimit = txnAmountLimit;
	}
	public int getTxnNumberLimit() {
		return txnNumberLimit;
	}
	public void setTxnNumberLimit(int txnNumberLimit) {
		this.txnNumberLimit = txnNumberLimit;
	}
	public float getTxnAmountPerDayLimit() {
		return txnAmountPerDayLimit;
	}
	public void setTxnAmountPerDayLimit(int txnAmountPerDayLimit) {
		this.txnAmountPerDayLimit = txnAmountPerDayLimit;
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
