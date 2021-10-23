package com.zerotouch.components.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerotouch.EntityScan.bean.SuperBean;
import com.zerotouch.components.webservicemodel.WebServiceResponseModel;
import com.zerotouch.util.ZeroTouchConstants;

@Service
public class SuperServiceImpl implements SuperService {

	@Autowired
	WebServiceResponseModel webServiceResponseModel;
	
	public WebServiceResponseModel setResponseOK(SuperBean superbean) {
		webServiceResponseModel.setResponseCode(ZeroTouchConstants.RESPONSE_CODE_OK);
		webServiceResponseModel.setResponseMessage(ZeroTouchConstants.RESPONSE_MESSAGE_OK);
		webServiceResponseModel.setSuperBean(superbean);
		return webServiceResponseModel;
	}
	
	public WebServiceResponseModel setResponseDataNotFound(SuperBean superbean) {
		webServiceResponseModel.setResponseCode(ZeroTouchConstants.RESPONSE_CODE_DATA_NOT_FOUND);
		webServiceResponseModel.setResponseMessage(ZeroTouchConstants.RESPONSE_MESSAGE_DATA_NOT_FOUND);
		webServiceResponseModel.setSuperBean(superbean);
		return webServiceResponseModel;
	}
	
	public WebServiceResponseModel setResponseInvalidRequest(SuperBean superbean) {
		webServiceResponseModel.setResponseCode(ZeroTouchConstants.RESPONSE_CODE_INVALID_REQUEST);
		webServiceResponseModel.setResponseMessage(ZeroTouchConstants.RESPONSE_MESSAGE_INVALID_REQUEST);
		webServiceResponseModel.setSuperBean(superbean);
		return webServiceResponseModel;
	}
	
	public WebServiceResponseModel setResponseInvalidPassword(SuperBean superbean) {
		webServiceResponseModel.setResponseCode(ZeroTouchConstants.RESPONSE_CODE_INCORRECT_PASSWORD);
		webServiceResponseModel.setResponseMessage(ZeroTouchConstants.RESPONSE_MESSAGE_INCORRECT_PASSWORD);
		webServiceResponseModel.setSuperBean(superbean);
		return webServiceResponseModel;
	}
	
	public WebServiceResponseModel setResponseInsufficientBalance(SuperBean superbean) {
		webServiceResponseModel.setResponseCode(ZeroTouchConstants.RESPONSE_CODE_INCORRECT_PASSWORD);
		webServiceResponseModel.setResponseMessage(ZeroTouchConstants.RESPONSE_MESSAGE_INCORRECT_PASSWORD);
		webServiceResponseModel.setSuperBean(superbean);
		return webServiceResponseModel;
	}
	
	public WebServiceResponseModel setResponseDataAlreadyExists(SuperBean superbean) {
		webServiceResponseModel.setResponseCode(ZeroTouchConstants.RESPONSE_CODE_DATA_ALREADY_EXISTS);
		webServiceResponseModel.setResponseMessage(ZeroTouchConstants.RESPONSE_MESSAGE_DATA_ALREADY_EXISTS);
		webServiceResponseModel.setSuperBean(superbean);
		return webServiceResponseModel;
	}
	
}
