package com.zerotouch.components.webservicemodel;

import com.zerotouch.EntityScan.bean.SuperBean;

public class InitiatePaymentResponseModel extends SuperBean {

	private String responseMessage;
	private String userDeviceId;
	private boolean amountInRange;
	private boolean trustyMerchant;
	private boolean sufficientBalance;
	private boolean isPaymentDone;
	
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getUserDeviceId() {
		return userDeviceId;
	}
	public void setUserDeviceId(String userDeviceId) {
		this.userDeviceId = userDeviceId;
	}
	public boolean isAmountInRange() {
		return amountInRange;
	}
	public void setAmountInRange(boolean amountInRange) {
		this.amountInRange = amountInRange;
	}
	public boolean isTrustyMerchant() {
		return trustyMerchant;
	}
	public void setTrustyMerchant(boolean trustyMerchant) {
		this.trustyMerchant = trustyMerchant;
	}
	public boolean isSufficientBalance() {
		return sufficientBalance;
	}
	public void setSufficientBalance(boolean sufficientBalance) {
		this.sufficientBalance = sufficientBalance;
	}
	public boolean isPaymentDone() {
		return isPaymentDone;
	}
	public void setPaymentDone(boolean isPaymentDone) {
		this.isPaymentDone = isPaymentDone;
	}
	
	
}
