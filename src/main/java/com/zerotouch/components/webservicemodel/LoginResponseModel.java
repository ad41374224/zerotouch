package com.zerotouch.components.webservicemodel;

import com.zerotouch.EntityScan.bean.SuperBean;
import com.zerotouch.EntityScan.bean.User;

public class LoginResponseModel extends SuperBean {

	private String message;

	private User user;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
