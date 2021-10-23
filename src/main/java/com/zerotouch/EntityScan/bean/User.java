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

import org.hibernate.annotations.ColumnDefault;

@Entity(name = "All_User")
public class User extends SuperBean {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
	@SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_ID_SEQ", allocationSize = 1, initialValue = 10001)
	private Integer id;
	
	@Column(name = "f_name", nullable = false)
	private String firstName;
	
	@Column(name = "l_name", nullable = false)
	private String lastName;
	
	@Column(name = "user_type", nullable = false)
	private String userType;
	
	@Column(name = "device_id")
	private String deviceId;
	
	@Column(name = "mobile_number", nullable = false, unique = true)
	private long mobileNo;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@OneToOne
	@JoinColumn(name = "user_add_id", referencedColumnName = "id")
	private UserAddress userAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private UserWallet userWallet;
	
	@Column(name = "status")
	private String status;

	@Column(name = "created_on", nullable = false)
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "mcht_reg_no")
	private String merchantRegNo;
	
	@Column(name = "nfctagdata")
	private String nfcTagData;
	
	@ColumnDefault("2000")
	@Column(name = "nfc_pwd_free_limit", nullable = false)
	private int nfcPwdFreeLimit;
	
	@Column(name = "nfc_password")
	private String nfcPassword;
	
	public User() {
		
	}
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMerchantRegNo() {
		return merchantRegNo;
	}

	public void setMerchantRegNo(String merchantRegNo) {
		this.merchantRegNo = merchantRegNo;
	}

	public UserWallet getUserWallet() {
		return userWallet;
	}

	public void setUserWallet(UserWallet userWallet) {
		this.userWallet = userWallet;
	}

	public String getNfcTagData() {
		return nfcTagData;
	}

	public void setNfcTagData(String nfcTagData) {
		this.nfcTagData = nfcTagData;
	}

	public int getNfcPwdFreeLimit() {
		return nfcPwdFreeLimit;
	}

	public void setNfcPwdFreeLimit(int nfcPwdFreeLimit) {
		this.nfcPwdFreeLimit = nfcPwdFreeLimit;
	}

	public String getNfcPassword() {
		return nfcPassword;
	}

	public void setNfcPassword(String nfcPassword) {
		this.nfcPassword = nfcPassword;
	}
	
}
