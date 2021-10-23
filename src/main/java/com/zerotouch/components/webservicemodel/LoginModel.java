package com.zerotouch.components.webservicemodel;

import com.zerotouch.EntityScan.bean.SuperBean;

public class LoginModel extends SuperBean {

	private long mobileNumber;
	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
}
