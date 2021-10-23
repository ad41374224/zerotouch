package com.zerotouch.components.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zerotouch.EntityScan.bean.TrustyCustomer;
import com.zerotouch.EntityScan.bean.UnregisteredUser;
import com.zerotouch.EntityScan.bean.User;
import com.zerotouch.EntityScan.bean.UserLogin;
import com.zerotouch.JpaRepositories.repo.UserAddressRepository;
import com.zerotouch.components.service.UserRegisterService;
import com.zerotouch.components.webservicemodel.LoginModel;
import com.zerotouch.components.webservicemodel.LoginResponseModel;
import com.zerotouch.components.webservicemodel.NFCTagDataModel;
import com.zerotouch.components.webservicemodel.TrustyCustomerResponseModel;
import com.zerotouch.components.webservicemodel.WebServiceResponseModel;
import com.zerotouch.util.UtilMethods;

@RestController
public class UserRegisterController {
	
	@Autowired
	UserRegisterService userRegisterService;
	
	@Autowired
	UserAddressRepository userAddressRepository;
	
	@Autowired
	WebServiceResponseModel webServiceResponseModel;
	
	@PostMapping(value = "/checkifregister")
	@ResponseBody
	public WebServiceResponseModel testRegistration(@RequestBody UnregisteredUser unregisteredUser) {
		if(unregisteredUser!=null && UtilMethods.validateMobileNo(unregisteredUser.getMobileNo())) {
			unregisteredUser = userRegisterService.testRegistration(unregisteredUser);
			if(unregisteredUser!= null) {
				webServiceResponseModel = userRegisterService.setResponseOK(unregisteredUser);
			}
		}else {
			webServiceResponseModel = userRegisterService.setResponseInvalidRequest(unregisteredUser);
		}
		return webServiceResponseModel;
	}
	
	@PostMapping(value = "/verifyotp")
	@ResponseBody
	public WebServiceResponseModel verifyOTP(@RequestBody UnregisteredUser unregisteredUser) {
		if(unregisteredUser!=null && UtilMethods.validateMobileNo(unregisteredUser.getMobileNo())) {
			unregisteredUser = userRegisterService.verifyOTP(unregisteredUser);
			if(unregisteredUser!= null) {
				webServiceResponseModel = userRegisterService.setResponseOK(unregisteredUser);
			}
		}else {
			webServiceResponseModel = userRegisterService.setResponseInvalidRequest(unregisteredUser);
		}
		return webServiceResponseModel;
	}
	
	@PostMapping(value = "/registerUser")
	@ResponseBody
	public WebServiceResponseModel registerUser(@RequestBody UserLogin userLogin) {
		if(userRegisterService.isUserBeanValidToRegister(userLogin)) {
			User existingUser =  userRegisterService.getUserDetailsByMobNo(userLogin.getUser().getMobileNo());
			if(existingUser != null){
				webServiceResponseModel = userRegisterService.setResponseDataAlreadyExists(existingUser);
			}else {
				userLogin = userRegisterService.registerUser(userLogin);
				webServiceResponseModel = userRegisterService.setResponseOK(userLogin);
			}
		}else {
			webServiceResponseModel = userRegisterService.setResponseInvalidRequest(userLogin);
		}
		return webServiceResponseModel;
	}
	
	@PostMapping(value = "/userdetailsbymobno")
	@ResponseBody
	public WebServiceResponseModel getUserDetailsByMobNo(@RequestBody User user) {
		if(user!=null && user.getMobileNo()>1000000000L && user.getMobileNo()<10000000000L) {
			user = userRegisterService.getUserDetailsByMobNo(user.getMobileNo());
			webServiceResponseModel = userRegisterService.setResponseOK(user);
		}else {
			webServiceResponseModel = userRegisterService.setResponseInvalidRequest(user);
		}
		return webServiceResponseModel;
	}
	
	@GetMapping(value = "/alluserdetails")
	@ResponseBody
	public List<UserLogin> getAllUserDetails() {
		return userRegisterService.getAllUsers();
	}
	
	@PostMapping(value = "/addtrustymerchant")
	@ResponseBody
	public WebServiceResponseModel addTrustyMerchant(@RequestBody TrustyCustomer trustyCustomer) {
		TrustyCustomerResponseModel trustMerchantRes = null;
		if(userRegisterService.isTrustyMerchantValid(trustyCustomer)) {
			trustMerchantRes = userRegisterService.addTrustyMerchant(trustyCustomer);
			if (trustMerchantRes.isSuccess()) {
				webServiceResponseModel = userRegisterService.setResponseOK(trustMerchantRes);
			}
			else {
				webServiceResponseModel = userRegisterService.setResponseInvalidRequest(trustMerchantRes);
			}
		}
		else {
			trustMerchantRes = new TrustyCustomerResponseModel();
			trustMerchantRes.setMessage("Invalid Request");
			webServiceResponseModel = userRegisterService.setResponseInvalidRequest(trustMerchantRes);
		}
		return webServiceResponseModel;
	}
	
	@PostMapping(value = "/login")
	@ResponseBody
	public WebServiceResponseModel login(@RequestBody LoginModel loginModel) {
		if(userRegisterService.isUserBeanValidToLogin(loginModel)) {
			LoginResponseModel loginReponse = userRegisterService.login(loginModel);
			if (loginReponse.getUser() != null) {
				webServiceResponseModel = userRegisterService.setResponseOK(loginReponse);
			} 
			else {
				webServiceResponseModel = userRegisterService.setResponseDataNotFound(loginModel);
			}
		} 
		else {
			webServiceResponseModel = userRegisterService.setResponseInvalidRequest(loginModel);
		}
			
		return webServiceResponseModel;
	}
	
	@PutMapping(value = "/updateUser")
	@ResponseBody
	public WebServiceResponseModel updateUser(@RequestBody UserLogin userLogin) {
		if(userLogin.getId() != 0) {
			UserLogin existingUser = userRegisterService.updateUser(userLogin);
			if(existingUser != null){
				webServiceResponseModel = userRegisterService.setResponseOK(existingUser);
			}else {
				webServiceResponseModel = userRegisterService.setResponseInvalidRequest(userLogin);
			}
		}else {
			webServiceResponseModel = userRegisterService.setResponseInvalidRequest(userLogin);
		}
		return webServiceResponseModel;
	}
	
	@PostMapping(value = "/updateNFCTagData")
	@ResponseBody
	public WebServiceResponseModel updateNFCTagData(@RequestBody NFCTagDataModel NFCTagDataModel) {
		if(UtilMethods.validateMobileNo(NFCTagDataModel.getCustMobileNumber()) && NFCTagDataModel.getNfcTagData() != null && !NFCTagDataModel.getNfcTagData().equals("")) {
			User user = userRegisterService.updateNFCData(NFCTagDataModel);
			if(user != null) {
				webServiceResponseModel = userRegisterService.setResponseOK(user);
			} else {
				webServiceResponseModel = userRegisterService.setResponseDataNotFound(NFCTagDataModel);
			}
		}else {
			webServiceResponseModel = userRegisterService.setResponseInvalidRequest(NFCTagDataModel);
		}
		return webServiceResponseModel;
	}
	
}
