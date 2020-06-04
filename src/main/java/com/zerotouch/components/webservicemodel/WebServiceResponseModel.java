package com.zerotouch.components.webservicemodel;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zerotouch.EntityScan.bean.SuperBean;

@Component
public class WebServiceResponseModel {
	
	private String responseCode;
	private String responseMessage;
	@JsonIgnore
	private String authString;
	private SuperBean superBean;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getAuthString() {
		return authString;
	}
	public void setAuthString(String authString) {
		this.authString = authString;
	}
	public SuperBean getSuperBean() {
		return superBean;
	}
	public void setSuperBean(SuperBean superBean) {
		this.superBean = superBean;
	}
	
}
