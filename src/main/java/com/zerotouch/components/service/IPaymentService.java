package com.zerotouch.components.service;

import com.zerotouch.components.webservicemodel.AddMoneyModel;
import com.zerotouch.components.webservicemodel.AddMoneyResponseModel;
import com.zerotouch.components.webservicemodel.GetTransactionsResponseModel;
import com.zerotouch.components.webservicemodel.InitiatePaymentModel;
import com.zerotouch.components.webservicemodel.InitiatePaymentResponseModel;

public interface IPaymentService extends SuperService {
	
	public boolean validateInitiatePaymentRequest(InitiatePaymentModel initiatePaymentModel);
	
	public boolean validateInitiatePaymentNFCRequest(InitiatePaymentModel initiatePaymentModel);
	
	public boolean validateInitiateNFCPaymentRequest(InitiatePaymentModel initiatePaymentModel);

	public InitiatePaymentResponseModel initiatePayment(InitiatePaymentModel initiatePayment);
	
	public InitiatePaymentResponseModel initiateNFCPayment(InitiatePaymentModel initiatePayment);
	
	public AddMoneyResponseModel addMoney(AddMoneyModel addMoney);
	
	public GetTransactionsResponseModel getTransactions(int userId, int pageNo, int pageSize);
	
	public InitiatePaymentResponseModel deductAmount(InitiatePaymentModel initiatePayment);
	
	public InitiatePaymentResponseModel deductNFCAmount(InitiatePaymentModel initiatePayment);
}
