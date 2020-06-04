package com.zerotouch.components.webservicemodel;

import com.zerotouch.EntityScan.bean.SuperBean;

public class InitiatePaymentModel extends SuperBean {

	private int merchantId;
	private long custMobileNumber;
	private float amount;
	
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public long getCustMobileNumber() {
		return custMobileNumber;
	}
	public void setCustMobileNumber(long custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}
