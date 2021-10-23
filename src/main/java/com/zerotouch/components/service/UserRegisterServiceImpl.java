package com.zerotouch.components.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerotouch.EntityScan.bean.TrustyCustomer;
import com.zerotouch.EntityScan.bean.UnregisteredUser;
import com.zerotouch.EntityScan.bean.User;
import com.zerotouch.EntityScan.bean.UserAddress;
import com.zerotouch.EntityScan.bean.UserLogin;
import com.zerotouch.EntityScan.bean.UserWallet;
import com.zerotouch.JpaRepositories.repo.TrustyMerchantRepository;
import com.zerotouch.JpaRepositories.repo.UnregisterUserRepositiory;
import com.zerotouch.JpaRepositories.repo.UserAddressRepository;
import com.zerotouch.JpaRepositories.repo.UserLoginRepositiory;
import com.zerotouch.JpaRepositories.repo.UserRepositiory;
import com.zerotouch.JpaRepositories.repo.UserWalletRepositiory;
import com.zerotouch.components.webservicemodel.LoginModel;
import com.zerotouch.components.webservicemodel.LoginResponseModel;
import com.zerotouch.components.webservicemodel.NFCTagDataModel;
import com.zerotouch.components.webservicemodel.TrustyCustomerResponseModel;
import com.zerotouch.util.UtilMethods;
import com.zerotouch.util.ZeroTouchConstants;

@Service
public class UserRegisterServiceImpl extends SuperServiceImpl implements UserRegisterService {
	
	@Autowired
	private UnregisterUserRepositiory unregisterUserRepositiory;
	
	@Autowired
	private UserRepositiory userRepositiory;
	
	@Autowired
	private UserAddressRepository userAddressRepository;
	
	@Autowired
	private UserLoginRepositiory userLoginRepositiory;
	
	@Autowired
	private UserWalletRepositiory userWalletRepositiory;
	
	@Autowired
	private TrustyMerchantRepository trustyMerchantRepositiory;
	
	private static final int MAX_LOGIN_RETRIES = 3;
	private static final int FAILED_LOGIN_WINDOW = 2;
	
	public UnregisteredUser testRegistration(UnregisteredUser unregisteredUser) {
		List<UnregisteredUser> unregisterUserList = unregisterUserRepositiory.findByMobileNo(unregisteredUser.getMobileNo());
		if(unregisterUserList.size() > 0) {
			return unregisterUserList.get(0);
		}else {
			return tempregisterUser(unregisteredUser);
		}
	}
	
	private UnregisteredUser tempregisterUser(UnregisteredUser unregisteredUser) {
		
		unregisteredUser.setOTP(1234);
		unregisteredUser.setOTPVerified(false);
		unregisteredUser.setRegistered(false);
		unregisteredUser.setCreatedOn(new Date());
		
		unregisterUserRepositiory.save(unregisteredUser);
		
		return unregisteredUser;
		
	}
	
	public UnregisteredUser verifyOTP(UnregisteredUser unregisteredUser) {
		List<UnregisteredUser> unregisteredUserList = unregisterUserRepositiory.findByMobileNo(unregisteredUser.getMobileNo());
		UnregisteredUser existingUnregisteredUser = null;
		if(unregisteredUserList.size() == 1) {
			existingUnregisteredUser = unregisteredUserList.get(0);
		}
		existingUnregisteredUser.setOTPVerified(true);
		existingUnregisteredUser.setUpdatedOn(new Date());
		unregisterUserRepositiory.save(existingUnregisteredUser);
		return existingUnregisteredUser;
	}
	
	@Transactional
	public UserLogin registerUser(UserLogin userLogin) {
		if(userLogin.getUser().getUserAddress() != null) {
			userLogin.getUser().getUserAddress().setCreatedOn(new Date());
			userAddressRepository.save(userLogin.getUser().getUserAddress());
		}
		
		UserWallet userWallet = new UserWallet();
		userWallet.setCreatedOn(new Date());
		userWallet.setBalance(ZeroTouchConstants.USER_SWALLET_MIN_BAL);
		userWallet.setMaxBalanceLimit(ZeroTouchConstants.USER_SWALLET_MAX_BAL);
		userLogin.getUser().setUserWallet(userWallet);
		
		userLogin.getUser().setCreatedOn(new Date());
		userLogin.getUser().setStatus(ZeroTouchConstants.USER_STATUS_ACTIVE);
		
		userLogin.setCreatedOn(new Date());
		userLogin.setExpiryDate(ZeroTouchConstants.USER_EXPIRY_DATE);
		userLogin.setStatus(ZeroTouchConstants.USER_STATUS_ACTIVE);
		userLogin = userLoginRepositiory.save(userLogin);
		
		if(userLogin.getId()!= 0) {
			List<UnregisteredUser> unregisteredUserList = unregisterUserRepositiory.findByMobileNo(userLogin.getUser().getMobileNo());
			UnregisteredUser unregisteredUser = null;
			if(unregisteredUserList.size() == 1) {
				unregisteredUser = unregisteredUserList.get(0);
				unregisteredUser.setRegistered(true);
				unregisterUserRepositiory.save(unregisteredUser);
			}
			
		}
		
		return userLogin;
		
	}
	
	public User getUserDetailsByMobNo(long mobileNumber) {
		
		List<User> userList = userRepositiory.findByMobileNo(mobileNumber);
		if(userList != null && !userList.isEmpty())
			return userList.get(0);
		else
			return null;
		
	}
	
	public boolean isUserBeanValidToRegister(UserLogin userLogin) {
		if(userLogin!= null 
				&& userLogin.getUser()!=null  
				&& UtilMethods.validateMobileNo(userLogin.getUser().getMobileNo()) 
				&& userLogin.getUser().getFirstName() != null && !userLogin.getUser().getFirstName().trim().isEmpty() 
				&& userLogin.getUser().getLastName() != null && !userLogin.getUser().getLastName().trim().isEmpty() 
				&& userLogin.getUser().getEmail() != null && !userLogin.getUser().getEmail().trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
	public List<UserLogin> getAllUsers(){
		return userLoginRepositiory.findAll();
	}

	public boolean isUserBeanValidToLogin(LoginModel loginModel) {
		if(loginModel!= null 
				&& !loginModel.getPassword().equals("")
				&& UtilMethods.validateMobileNo(loginModel.getMobileNumber())) {
			return true;
		}
		return false;
	}
	
	@Override
	public TrustyCustomerResponseModel addTrustyMerchant(TrustyCustomer trustyCustomer) {
		TrustyCustomerResponseModel trustyCustomerResponse = new TrustyCustomerResponseModel();
		Optional<User> user = userRepositiory.findById(trustyCustomer.getUserId());
		Optional<User> merchant = userRepositiory.findById(trustyCustomer.getMrcntId());
		if (user.isPresent() && merchant.isPresent()
				&& user.get().getUserType().equalsIgnoreCase("Customer")
				&& merchant.get().getUserType().equalsIgnoreCase("Merchant")) {
			
			List<TrustyCustomer> trustyMerchants = trustyMerchantRepositiory
					.findByUserIdAndMerchantId(trustyCustomer.getUserId(), trustyCustomer.getMrcntId());
			if (trustyMerchants != null && !trustyMerchants.isEmpty()) {
				trustyCustomerResponse.setSuccess(false);
				trustyCustomerResponse.setMessage("Merchant is already added to your list");
			}
			else {
				trustyCustomer.setCreatedOn(new Date());
				trustyMerchantRepositiory.save(trustyCustomer);
				trustyCustomerResponse.setSuccess(true);
				trustyCustomerResponse.setMessage("Merchant is added to Trusty list successfully");
			}
			
		}
		else {
			trustyCustomerResponse.setSuccess(false);
			trustyCustomerResponse.setMessage("Invalid Request");
		}
		return trustyCustomerResponse;
	}

	@Override
	public boolean isTrustyMerchantValid(TrustyCustomer trustyCustomer) {
		if (trustyCustomer != null
				&& trustyCustomer.getUserId() != trustyCustomer.getMrcntId()
				&& trustyCustomer.getTrustedFlag() != null
				&& trustyCustomer.getTrustedFlag() != null
				&& (Character.toString(trustyCustomer.getTrustedFlag()).equalsIgnoreCase("Y")
						|| Character.toString(trustyCustomer.getTrustedFlag()).equalsIgnoreCase("N"))
				&& trustyCustomer.getTxnNumberLimit() > 0
				&& trustyCustomer.getTxnAmountLimit() > 0
				&& trustyCustomer.getTxnAmountPerDayLimit() > 0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public LoginResponseModel login(LoginModel loginModel) {
		LoginResponseModel loginReponse = new LoginResponseModel();
		User user = getUserDetailsByMobNo(loginModel.getMobileNumber());
		if (user != null) {
			Optional<UserLogin> userLogin = userLoginRepositiory.findById(user.getId());
			if (userLogin.isPresent() && userLogin.get().getPassword().equals(loginModel.getPassword())) {
				if (userLogin.get().getNoOfRetries() < MAX_LOGIN_RETRIES || checkForLoginWindow(userLogin.get())) {
					userLogin.get().setUpdatedOn(new Date());
					userLogin.get().setNoOfRetries(0);
					loginReponse.setUser(user);
				}
				else {
					userLogin.get().setUpdatedOn(new Date());
					userLogin.get().setNoOfRetries(userLogin.get().getNoOfRetries() + 1);
					loginReponse.setMessage("You have exceeded the number of login attempts. Please try again after " + FAILED_LOGIN_WINDOW + " hours.");
				}
			}
			else {
				if (userLogin.get().getNoOfRetries() < MAX_LOGIN_RETRIES) {
					userLogin.get().setUpdatedOn(new Date());
					userLogin.get().setNoOfRetries(userLogin.get().getNoOfRetries() + 1);
					loginReponse.setMessage("mobile number/Password is incorrect.");
				}
				else {
					userLogin.get().setUpdatedOn(new Date());
					userLogin.get().setNoOfRetries(userLogin.get().getNoOfRetries() + 1);
					loginReponse.setMessage("You have exceeded the number of login attempts. Please try again after " + FAILED_LOGIN_WINDOW + " hours.");
				}
			}
		}
		else {
			loginReponse.setMessage("mobile number/Password is incorrect.");
		}
		return loginReponse;
	}

	private boolean checkForLoginWindow(UserLogin userLogin) {
		if (userLogin.getUpdatedOn() != null) {
			long updatedTime = userLogin.getUpdatedOn().getTime();
			long currentTime = System.currentTimeMillis();
			long hourDiff = (currentTime - updatedTime) / (60 * 60 * 1000);
			if (hourDiff < FAILED_LOGIN_WINDOW) {
				return false;
			}
		}
		return true;
	}

	@Transactional
	@Override
	public UserLogin updateUser(UserLogin userLogin) {
		Optional<UserLogin> userLoginDB = userLoginRepositiory.findById(userLogin.getId());
		if (userLoginDB.isPresent()) {
			if (userLogin.getUser() != null) {
				User userDB = userLoginDB.get().getUser();
				updateUserParameters(userLogin.getUser(), userDB);
				userRepositiory.save(userDB);
			}
			if (userLogin.getPassword() != null) {
				userLoginDB.get().setPassword(userLogin.getPassword());
				userLoginDB.get().setUpdatedOn(new Date());
			}
			return userLoginDB.get();
		}
		return null;
	}

	private void updateUserParameters(User user, User userDB) {
		if (user.getDeviceId() != null) {
			userDB.setDeviceId(user.getDeviceId());
		}
		if (user.getEmail() != null) {
			userDB.setEmail(user.getEmail());
		}
		if (user.getFirstName() != null) {
			userDB.setFirstName(user.getFirstName());
		}
		if (user.getGender() != null) {
			userDB.setGender(user.getGender());
		}
		if (user.getLastName() != null) {
			userDB.setLastName(user.getLastName());
		}
		if (user.getMerchantRegNo() != null) {
			userDB.setMerchantRegNo(user.getMerchantRegNo());
		}
		if (user.getMobileNo() != 0) {
			userDB.setMobileNo(user.getMobileNo());
		}
		if (user.getUserType() != null) {
			userDB.setUserType(user.getUserType());
		}
		if (user.getUserAddress() != null) {
			updateAddressParameters(user.getUserAddress(), userDB.getUserAddress());
		}
		if (userDB.getUserAddress() != null) {
			userAddressRepository.save(userDB.getUserAddress());
		}
		userDB.setUpdatedOn(new Date());
		//wallet
	}

	private void updateAddressParameters(UserAddress userAddress, UserAddress userAddressDB) {
		if (userAddress.getCity() != null) {
			userAddressDB.setCity(userAddress.getCity());
		}
		if (userAddress.getCountry() != null) {
			userAddressDB.setCountry(userAddress.getCountry());
		}
		if (userAddress.getHouseNo() != null) {
			userAddressDB.setHouseNo(userAddress.getHouseNo());
		}
		if (userAddress.getState() != null) {
			userAddressDB.setState(userAddress.getState());
		}
		if (userAddress.getStreetBlockNo() != null) {
			userAddressDB.setStreetBlockNo(userAddress.getStreetBlockNo());
		}
		userAddressDB.setUpdatedOn(new Date());
	}
	
	public User updateNFCData(NFCTagDataModel NFCTagDataModel) {
		User user = getUserDetailsByMobNo(NFCTagDataModel.getCustMobileNumber());
		if(user!=null) {
			user.setNfcTagData(NFCTagDataModel.getNfcTagData());
			return userRepositiory.save(user);
		} else {
			return null;
		}
		
	}
}







