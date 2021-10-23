package com.zerotouch.components.webservicemodel;

import com.zerotouch.EntityScan.bean.SuperBean;

public class InitiatePaymentModel extends SuperBean {

	private int merchantId;
	private long custMobileNumber;
	private float amount;
	private String custNFCData;
	private boolean isAmountUnderPwdFreeLimit;
	private String custNFCPassword;
	
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
	public String getCustNFCData() {
		return custNFCData;
	}
	public void setCustNFCData(String custNFCData) {
		this.custNFCData = custNFCData;
	}
	public String getCustNFCPassword() {
		return custNFCPassword;
	}
	public void setCustNFCPassword(String custNFCPassword) {
		this.custNFCPassword = custNFCPassword;
	}
	public boolean isAmountUnderPwdFreeLimit() {
		return isAmountUnderPwdFreeLimit;
	}
	public void setAmountUnderPwdFreeLimit(boolean isAmountUnderPwdFreeLimit) {
		this.isAmountUnderPwdFreeLimit = isAmountUnderPwdFreeLimit;
	}
	
}
