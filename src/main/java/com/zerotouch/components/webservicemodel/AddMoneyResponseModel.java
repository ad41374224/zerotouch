package com.zerotouch.components.webservicemodel;

import com.zerotouch.EntityScan.bean.SuperBean;

public class AddMoneyResponseModel extends SuperBean {

	private String message;
	private boolean success;
	private float totalBalance;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public float getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(float totalBalance) {
		this.totalBalance = totalBalance;
	}
	
	
}
