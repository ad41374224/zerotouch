package com.zerotouch.components.webservicemodel;

import java.util.List;

import com.zerotouch.EntityScan.bean.SuperBean;

public class GetTransactionsResponseModel extends SuperBean {

	private float totalBalance;
	private List<TransactionDetail> transactions;
	
	public float getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(float totalBalance) {
		this.totalBalance = totalBalance;
	}
	public List<TransactionDetail> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<TransactionDetail> transactions) {
		this.transactions = transactions;
	}
	
	
}
