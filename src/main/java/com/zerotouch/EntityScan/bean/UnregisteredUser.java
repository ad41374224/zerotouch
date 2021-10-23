package com.zerotouch.EntityScan.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "Unregister_User")
public class UnregisteredUser extends SuperBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Unregister_User_SEQ")
	@SequenceGenerator(name = "Unregister_User_SEQ", sequenceName = "Unregister_User_ID_SEQ", allocationSize = 1, initialValue = 10001)
	private int id;
	
	@Column(name = "mobile_number", nullable = false, unique = true)
	private long mobileNo;
	
	@Column(name = "otp")
	private int OTP;
	
	@Column(name = "OTP_verified")
	private boolean isOTPVerified;
	
	@Column(name = "isRegistered")
	private boolean isRegistered;
	
	@Column(name = "created_on", nullable = false)
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;
	
	public UnregisteredUser() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public int getOTP() {
		return OTP;
	}
	public void setOTP(int oTP) {
		OTP = oTP;
	}
	public boolean isOTPVerified() {
		return isOTPVerified;
	}
	public void setOTPVerified(boolean isOTPVerified) {
		this.isOTPVerified = isOTPVerified;
	}
	public boolean isRegistered() {
		return isRegistered;
	}
	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
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
