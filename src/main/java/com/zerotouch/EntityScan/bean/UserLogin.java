package com.zerotouch.EntityScan.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "login")
public class UserLogin extends SuperBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOGIN_SEQ")
	@SequenceGenerator(name = "LOGIN_SEQ", sequenceName = "LOGIN_ID_SEQ", allocationSize = 1, initialValue = 10001)
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private User user;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "status", nullable = false)
	private String status;
	
	@Column(name = "expiry_date", nullable = false)
	private Date expiryDate;
	
	@Column(name = "n_of_retries")
	private int noOfRetries;
	
	@Column(name = "created_on", nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;
	
	public UserLogin() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getNoOfRetries() {
		return noOfRetries;
	}

	public void setNoOfRetries(int noOfRetries) {
		this.noOfRetries = noOfRetries;
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
