package com.zerotouch.components.service;

import com.zerotouch.EntityScan.bean.SuperBean;
import com.zerotouch.components.webservicemodel.WebServiceResponseModel;

public interface SuperService {

	public WebServiceResponseModel setResponseOK(SuperBean superbean);
	
	public WebServiceResponseModel setResponseInvalidRequest(SuperBean superbean);
	
	public WebServiceResponseModel setResponseDataNotFound(SuperBean superbean);
	
	public WebServiceResponseModel setResponseDataAlreadyExists(SuperBean superbean);
	
	public WebServiceResponseModel setResponseInvalidPassword(SuperBean superbean);
	
	public WebServiceResponseModel setResponseInsufficientBalance(SuperBean superbean);
	
}
