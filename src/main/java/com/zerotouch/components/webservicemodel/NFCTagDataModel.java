package com.zerotouch.components.webservicemodel;

import javax.persistence.Column;

import com.zerotouch.EntityScan.bean.SuperBean;

public class NFCTagDataModel extends SuperBean {

	private long custMobileNumber;
	private String nfcTagData;
	private int nfcPwdFreeLimit;
	private String nfcPassword;
	
	public long getCustMobileNumber() {
		return custMobileNumber;
	}
	public void setCustMobileNumber(long custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
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
