package com.zerotouch.components.service;

import java.util.List;

import com.zerotouch.EntityScan.bean.TrustyCustomer;
import com.zerotouch.EntityScan.bean.UnregisteredUser;
import com.zerotouch.EntityScan.bean.User;
import com.zerotouch.EntityScan.bean.UserLogin;
import com.zerotouch.components.webservicemodel.LoginModel;
import com.zerotouch.components.webservicemodel.LoginResponseModel;
import com.zerotouch.components.webservicemodel.TrustyCustomerResponseModel;

public interface UserRegisterService extends SuperService {
	
	public UnregisteredUser testRegistration(UnregisteredUser nnregisteredUser);
	
	public UnregisteredUser verifyOTP(UnregisteredUser unregisteredUser);
	
	public UserLogin registerUser(UserLogin userLogin);
	
	public User getUserDetailsByMobNo(long mobileNumber);
	
	public boolean isUserBeanValidToRegister(UserLogin userLogin);
	
	public List<UserLogin> getAllUsers();
	
	public TrustyCustomerResponseModel addTrustyMerchant(TrustyCustomer trustyCustomer);

	public boolean isTrustyMerchantValid(TrustyCustomer trustyCustomer);
	
	public boolean isUserBeanValidToLogin(LoginModel loginModel);
	
	public LoginResponseModel login(LoginModel loginModel);
	
	public UserLogin updateUser(UserLogin userLogin);
	
}
