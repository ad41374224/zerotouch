package com.zerotouch.components.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zerotouch.components.service.IPaymentService;
import com.zerotouch.components.webservicemodel.AddMoneyModel;
import com.zerotouch.components.webservicemodel.AddMoneyResponseModel;
import com.zerotouch.components.webservicemodel.GetTransactionsResponseModel;
import com.zerotouch.components.webservicemodel.InitiatePaymentModel;
import com.zerotouch.components.webservicemodel.InitiatePaymentResponseModel;
import com.zerotouch.components.webservicemodel.WebServiceResponseModel;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	WebServiceResponseModel webServiceResponseModel;
	
	@Autowired
	IPaymentService paymentService;
	
	@PostMapping(value = "/initiatePayment")
	@ResponseBody
	public WebServiceResponseModel initiatePayment(@RequestBody InitiatePaymentModel initiatePayment) {
		if(paymentService.validateInitiatePaymentRequest(initiatePayment)) {
			InitiatePaymentResponseModel initiatePaymentResponse = paymentService.initiatePayment(initiatePayment);
			if (initiatePaymentResponse.isAmountInRange()
					&& initiatePaymentResponse.isSufficientBalance()
					&& initiatePaymentResponse.isTrustyMerchant()) {
				webServiceResponseModel = paymentService.setResponseOK(initiatePaymentResponse);
			}
			else {
				webServiceResponseModel = paymentService.setResponseInvalidRequest(initiatePaymentResponse);
			}
		}
		else {
			webServiceResponseModel = paymentService.setResponseInvalidRequest(initiatePayment);
		}
		return webServiceResponseModel;
	}
	
	@PostMapping(value = "/initiatePaymentViaNFC")
	@ResponseBody
	public WebServiceResponseModel initiatePaymentViaNFC(@RequestBody InitiatePaymentModel initiatePayment) {
		if(paymentService.validateInitiateNFCPaymentRequest(initiatePayment)) {
			InitiatePaymentResponseModel initiatePaymentResponse = paymentService.initiateNFCPayment(initiatePayment);
			if (initiatePaymentResponse.isSufficientBalance()) {
				webServiceResponseModel = paymentService.setResponseOK(initiatePaymentResponse);
			}
			else {
				webServiceResponseModel = paymentService.setResponseInvalidRequest(initiatePaymentResponse);
			}
		}
		else {
			webServiceResponseModel = paymentService.setResponseInvalidRequest(initiatePayment);
		}
		return webServiceResponseModel;
	}
	
	@PostMapping(value = "/deductamount")
	@ResponseBody
	public WebServiceResponseModel deductAmount(@RequestBody InitiatePaymentModel initiatePayment) {
		if(paymentService.validateInitiatePaymentRequest(initiatePayment)) {
			InitiatePaymentResponseModel initiatePaymentResponse = paymentService.deductAmount(initiatePayment);
			if (initiatePaymentResponse.isAmountInRange()
					&& initiatePaymentResponse.isSufficientBalance()
					&& initiatePaymentResponse.isTrustyMerchant()) {
				webServiceResponseModel = paymentService.setResponseOK(initiatePaymentResponse);
			}
			else {
				webServiceResponseModel = paymentService.setResponseInvalidRequest(initiatePaymentResponse);
			}
		}
		else {
			webServiceResponseModel = paymentService.setResponseInvalidRequest(initiatePayment);
		}
		return webServiceResponseModel;
	}
	
	@PostMapping(value = "/deductNFCAmount")
	@ResponseBody
	public WebServiceResponseModel deductNFCAmount(@RequestBody InitiatePaymentModel initiatePayment) {
		if(paymentService.validateInitiatePaymentNFCRequest(initiatePayment)) {
			InitiatePaymentResponseModel initiatePaymentResponse = paymentService.deductNFCAmount(initiatePayment);
			if (initiatePaymentResponse.isPaymentDone()) {
				webServiceResponseModel = paymentService.setResponseOK(initiatePaymentResponse);
			}else if (!initiatePaymentResponse.isPasswordCorrect()) {
				webServiceResponseModel = paymentService.setResponseInvalidPassword(initiatePaymentResponse);
			}else if(!initiatePaymentResponse.isSufficientBalance()) {
				webServiceResponseModel = paymentService.setResponseInsufficientBalance(initiatePaymentResponse);
			}else {
				webServiceResponseModel = paymentService.setResponseInvalidRequest(initiatePayment);
			}
		}else {
			webServiceResponseModel = paymentService.setResponseInvalidRequest(initiatePayment);
		}
		return webServiceResponseModel;
	}
	
	@PostMapping(value = "/addmoney")
	@ResponseBody
	public WebServiceResponseModel addMoney(@RequestBody AddMoneyModel addMoney) {
		AddMoneyResponseModel addMoneyResponseModel = null;
		if (addMoney.getAmount() <= 0) {
			addMoneyResponseModel = new AddMoneyResponseModel();
			addMoneyResponseModel.setSuccess(false);
			addMoneyResponseModel.setMessage("Invalid Amount");
			
			webServiceResponseModel = paymentService.setResponseInvalidRequest(addMoneyResponseModel);
		}
		else {
			addMoneyResponseModel = paymentService.addMoney(addMoney);
			if (addMoneyResponseModel.isSuccess()) {
				webServiceResponseModel = paymentService.setResponseOK(addMoneyResponseModel);
			}
			else {
				webServiceResponseModel = paymentService.setResponseInvalidRequest(addMoneyResponseModel);
			}
		}
		return webServiceResponseModel;
	}
	
	@GetMapping(value = "/getTransactions")
	@ResponseBody
	public WebServiceResponseModel getTransactions(@RequestHeader(value="pageNo") int pageNo,
			@RequestHeader(value="pageSize") int pageSize,
			@RequestHeader(value="userId") int userId) {
		GetTransactionsResponseModel response = paymentService.getTransactions(userId, pageNo, pageSize);
		webServiceResponseModel = paymentService.setResponseOK(response);
		return webServiceResponseModel;
	}
	
	
	
}
