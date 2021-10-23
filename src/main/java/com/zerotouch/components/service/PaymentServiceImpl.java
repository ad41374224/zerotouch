package com.zerotouch.components.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zerotouch.EntityScan.bean.Transaction;
import com.zerotouch.EntityScan.bean.TrustyCustomer;
import com.zerotouch.EntityScan.bean.User;
import com.zerotouch.EntityScan.bean.UserWallet;
import com.zerotouch.JpaRepositories.repo.TransactionRepository;
import com.zerotouch.JpaRepositories.repo.TrustyMerchantRepository;
import com.zerotouch.JpaRepositories.repo.UserRepositiory;
import com.zerotouch.JpaRepositories.repo.UserWalletRepositiory;
import com.zerotouch.components.webservicemodel.AddMoneyModel;
import com.zerotouch.components.webservicemodel.AddMoneyResponseModel;
import com.zerotouch.components.webservicemodel.GetTransactionsResponseModel;
import com.zerotouch.components.webservicemodel.InitiatePaymentModel;
import com.zerotouch.components.webservicemodel.InitiatePaymentResponseModel;
import com.zerotouch.components.webservicemodel.TransactionDetail;
import com.zerotouch.util.UtilMethods;

@Service
public class PaymentServiceImpl extends SuperServiceImpl implements IPaymentService {

	@Autowired
	private UserRepositiory userRepositiory;
	@Autowired
	private TrustyMerchantRepository trustyMerchantRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private UserWalletRepositiory userWalletRepositiory;
	
	public boolean validateInitiatePaymentNFCRequest(InitiatePaymentModel initiatePaymentModel) {
		try {
			if(initiatePaymentModel.getAmount()> 0.0 && initiatePaymentModel.getMerchantId() > 0){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validateInitiatePaymentRequest(InitiatePaymentModel initiatePaymentModel) {
		try {
			if(UtilMethods.validateMobileNo(initiatePaymentModel.getCustMobileNumber()) && initiatePaymentModel.getAmount()> 0.0 && initiatePaymentModel.getMerchantId() > 0){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validateInitiateNFCPaymentRequest(InitiatePaymentModel initiatePaymentModel) {
		try {
			if(UtilMethods.validateMobileNo(initiatePaymentModel.getCustMobileNumber()) && initiatePaymentModel.getAmount()> 0.0 && initiatePaymentModel.getMerchantId() > 0 && initiatePaymentModel.getCustNFCData() !=null && !initiatePaymentModel.getCustNFCData().equals("")){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public InitiatePaymentResponseModel initiatePayment(InitiatePaymentModel initiatePayment) {
		InitiatePaymentResponseModel initiatePaymentResponse = new InitiatePaymentResponseModel();
		List<User> customers = userRepositiory.findByMobileNo(initiatePayment.getCustMobileNumber());
		Optional<User> merchant = userRepositiory.findById(initiatePayment.getMerchantId());
			
		if (customers != null && customers.size() == 1 && customers.get(0).getUserType().equalsIgnoreCase("Customer")
				&& merchant.isPresent() && merchant.get().getUserType().equalsIgnoreCase("Merchant")) {
			User customer = customers.get(0);
			if (customer.getUserWallet() != null
					&& customer.getUserWallet().getBalance() >= initiatePayment.getAmount()) {
				List<TrustyCustomer> trustyMerchants = trustyMerchantRepository
						.findByUserIdAndMerchantId(customer.getId(), initiatePayment.getMerchantId());
				if (trustyMerchants != null && trustyMerchants.size() == 1) {
					TrustyCustomer trustyMerchant = trustyMerchants.get(0);
					if (trustyMerchant.getTxnAmountLimit() >= initiatePayment.getAmount()) {
						List<Transaction> transactions = transactionRepository.findByUserIdAndMerchantId(customer.getId(), initiatePayment.getMerchantId());
						if (validateTransactionLimits(trustyMerchant, transactions)) {
							initiatePaymentResponse.setAmountInRange(true);
							initiatePaymentResponse.setSufficientBalance(true);
							initiatePaymentResponse.setTrustyMerchant(true);
							initiatePaymentResponse.setUserDeviceId(customer.getDeviceId());
							initiatePaymentResponse.setResponseMessage("All good to initate payment");
						}
						else {
							initiatePaymentResponse.setAmountInRange(false);
							initiatePaymentResponse.setResponseMessage("Transaction Limit exceeded");
						}
					}
					else {
						initiatePaymentResponse.setAmountInRange(false);
						initiatePaymentResponse.setResponseMessage("Transaction Amount is more than the set limit");
					}
				}
				else {
					initiatePaymentResponse.setTrustyMerchant(false);
					initiatePaymentResponse.setResponseMessage("Merchant is not trusted.");
				}
			}
			else {
				initiatePaymentResponse.setResponseMessage("Insufficient Balance.");
				initiatePaymentResponse.setSufficientBalance(false);
			}
		}
		else {
			initiatePaymentResponse.setResponseMessage("Customer/Merchant information is not correct.");
		}
		return initiatePaymentResponse;
	}
	
	@Override
	public InitiatePaymentResponseModel initiateNFCPayment(InitiatePaymentModel initiatePayment) {
		InitiatePaymentResponseModel initiatePaymentResponse = new InitiatePaymentResponseModel();
		List<User> customers = userRepositiory.findByMobileNo(initiatePayment.getCustMobileNumber());
		Optional<User> merchant = userRepositiory.findById(initiatePayment.getMerchantId());
			
		if (customers != null && customers.size() == 1 && customers.get(0).getUserType().equalsIgnoreCase("Customer")
				&& merchant.isPresent() && merchant.get().getUserType().equalsIgnoreCase("Merchant")) {
			User customer = customers.get(0);
			if (customer.getUserWallet() != null
					&& customer.getUserWallet().getBalance() >= initiatePayment.getAmount()) {
				initiatePaymentResponse.setAmountInRange(true);
				initiatePaymentResponse.setSufficientBalance(true);
				initiatePaymentResponse.setTrustyMerchant(true);
				initiatePaymentResponse.setUserDeviceId(customer.getDeviceId());
				initiatePaymentResponse.setCustNFTagCData(customer.getNfcTagData());
				initiatePaymentResponse.setNfcPwdFreeLimit(customer.getNfcPwdFreeLimit());
				initiatePaymentResponse.setCustNFCPassword(customer.getNfcPassword());
				initiatePaymentResponse.setAmountUnderPwdFreeLimit(initiatePayment.getAmount()<=customer.getNfcPwdFreeLimit()? true: false);
				initiatePaymentResponse.setResponseMessage("All good to initate payment");	
			}
			else {
				initiatePaymentResponse.setResponseMessage("Insufficient Balance.");
				initiatePaymentResponse.setSufficientBalance(false);
			}
		}
		else {
			initiatePaymentResponse.setResponseMessage("Customer/Merchant information is not correct.");
		}
		return initiatePaymentResponse;
	}
	
	@Override
	@Transactional
	public InitiatePaymentResponseModel deductAmount(InitiatePaymentModel initiatePayment) {
		InitiatePaymentResponseModel initiatePaymentResponse = new InitiatePaymentResponseModel();
		List<User> customers = userRepositiory.findByMobileNo(initiatePayment.getCustMobileNumber());
		Optional<User> merchant = userRepositiory.findById(initiatePayment.getMerchantId());
			
		if (customers != null && customers.size() == 1 && customers.get(0).getUserType().equalsIgnoreCase("Customer")
				&& merchant.isPresent() && merchant.get().getUserType().equalsIgnoreCase("Merchant")) {
			User customer = customers.get(0);
			if (customer.getUserWallet() != null
					&& customer.getUserWallet().getBalance() >= initiatePayment.getAmount()) {
				List<TrustyCustomer> trustyMerchants = trustyMerchantRepository
						.findByUserIdAndMerchantId(customer.getId(), initiatePayment.getMerchantId());
				if (trustyMerchants != null && trustyMerchants.size() == 1) {
					TrustyCustomer trustyMerchant = trustyMerchants.get(0);
					if (trustyMerchant.getTxnAmountLimit() >= initiatePayment.getAmount()) {
						List<Transaction> transactions = transactionRepository.findByUserIdAndMerchantId(customer.getId(), initiatePayment.getMerchantId());
						if (validateTransactionLimits(trustyMerchant, transactions)) {
							
							User merchatToAdd = merchant.get();
							
							customer.getUserWallet().setBalance(customer.getUserWallet().getBalance()-initiatePayment.getAmount());
							customer.getUserWallet().setUpdatedOn(new Date());
							merchatToAdd.getUserWallet().setBalance(merchatToAdd.getUserWallet().getBalance()+initiatePayment.getAmount());
							merchatToAdd.getUserWallet().setUpdatedOn(new Date());
							
							userRepositiory.save(customer);
							userRepositiory.save(merchatToAdd);
							
							Transaction transaction = new Transaction();
							
							transaction.setComment("Transaction initiated by " + merchatToAdd.getFirstName() + merchatToAdd.getLastName());
							transaction.setCreatedOn(new Date());
							transaction.setTransactionAmount(initiatePayment.getAmount());
							transaction.setTransactionRefNumber(UUID.randomUUID().toString());
							transaction.setTransactionType('D');
							transaction.setUserId(customer.getId());
							transaction.setMerchantId(merchatToAdd.getId());
							
							transactionRepository.save(transaction);
							
							initiatePaymentResponse.setAmountInRange(true);
							initiatePaymentResponse.setSufficientBalance(true);
							initiatePaymentResponse.setTrustyMerchant(true);
							initiatePaymentResponse.setUserDeviceId(customer.getDeviceId());
							initiatePaymentResponse.setPaymentDone(true);
							initiatePaymentResponse.setResponseMessage("Payment Done.");
						}
						else {
							initiatePaymentResponse.setAmountInRange(false);
							initiatePaymentResponse.setResponseMessage("Transaction Limit exceeded");
						}
					}
					else {
						initiatePaymentResponse.setAmountInRange(false);
						initiatePaymentResponse.setResponseMessage("Transaction Amount is more than the set limit");
					}
				}
				else {
					initiatePaymentResponse.setTrustyMerchant(false);
					initiatePaymentResponse.setResponseMessage("Merchant is not trusted.");
				}
			}
			else {
				initiatePaymentResponse.setResponseMessage("Insufficient Balance.");
				initiatePaymentResponse.setSufficientBalance(false);
			}
		}
		else {
			initiatePaymentResponse.setResponseMessage("Customer/Merchant information is not correct.");
		}
		return initiatePaymentResponse;
	}
	
	@Override
	@Transactional
	public InitiatePaymentResponseModel deductNFCAmount(InitiatePaymentModel initiatePayment) {
		InitiatePaymentResponseModel initiatePaymentResponse = new InitiatePaymentResponseModel();
		List<User> customers = userRepositiory.findByNfcTagData(initiatePayment.getCustNFCData());
		Optional<User> merchant = userRepositiory.findById(initiatePayment.getMerchantId());
			
		if (customers != null && customers.size() == 1 && customers.get(0).getUserType().equalsIgnoreCase("Customer")
				&& merchant.isPresent() && merchant.get().getUserType().equalsIgnoreCase("Merchant")) {
			User customer = customers.get(0);
			if (customer.getUserWallet() != null && customer.getUserWallet().getBalance() >= initiatePayment.getAmount()) {
				initiatePaymentResponse.setSufficientBalance(true);
				if(initiatePayment.isAmountUnderPwdFreeLimit() || (!initiatePayment.isAmountUnderPwdFreeLimit() && initiatePayment.getCustNFCPassword()!= null && initiatePayment.getCustNFCPassword().equals(customer.getNfcPassword())) ) { 
					User merchatToAdd = merchant.get();
					customer.getUserWallet().setBalance(customer.getUserWallet().getBalance()-initiatePayment.getAmount());
					customer.getUserWallet().setUpdatedOn(new Date());
					merchatToAdd.getUserWallet().setBalance(merchatToAdd.getUserWallet().getBalance()+initiatePayment.getAmount());
					merchatToAdd.getUserWallet().setUpdatedOn(new Date());
					
					userRepositiory.save(customer);
					userRepositiory.save(merchatToAdd);
					
					Transaction transaction = new Transaction();
					
					transaction.setComment("Transaction initiated by " + merchatToAdd.getFirstName() + merchatToAdd.getLastName());
					transaction.setCreatedOn(new Date());
					transaction.setTransactionAmount(initiatePayment.getAmount());
					transaction.setTransactionRefNumber(UUID.randomUUID().toString());
					transaction.setTransactionType('D');
					transaction.setUserId(customer.getId());
					transaction.setMerchantId(merchatToAdd.getId());
					
					transactionRepository.save(transaction);
					
					initiatePaymentResponse.setAmountInRange(true);
					initiatePaymentResponse.setSufficientBalance(true);
					initiatePaymentResponse.setTrustyMerchant(true);
					initiatePaymentResponse.setUserDeviceId(customer.getDeviceId());
					initiatePaymentResponse.setPaymentDone(true);
					initiatePaymentResponse.setResponseMessage("Payment Done.");
				}else {
					initiatePaymentResponse.setResponseMessage("Incorrect Password");
					initiatePaymentResponse.setPaymentDone(false);
					initiatePaymentResponse.setPasswordCorrect(false);
				}
			}else {
				initiatePaymentResponse.setResponseMessage("Insufficient Balance.");
				initiatePaymentResponse.setSufficientBalance(false);
				initiatePaymentResponse.setPaymentDone(false);
			}
		}else {
			initiatePaymentResponse.setResponseMessage("Customer/Merchant information is not correct.");
			initiatePaymentResponse.setPaymentDone(false);
		}
		return initiatePaymentResponse;
	}

	private boolean validateTransactionLimits(TrustyCustomer trustyMerchant, List<Transaction> transactions) {
		if (transactions != null) {
			List<Transaction> todaysTransactions = transactions.stream().filter(transaction -> {
				LocalDate today = LocalDate.now();
				LocalDate date = transaction.getCreatedOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				return today.compareTo(date) == 0;
			}).collect(Collectors.toList());
			double totalTransactionAmt = todaysTransactions.stream().mapToDouble(obj -> obj.getTransactionAmount()).sum();
			if (todaysTransactions.size() >= trustyMerchant.getTxnNumberLimit()
					|| totalTransactionAmt >= trustyMerchant.getTxnAmountPerDayLimit()) {
				return false;
			}
		}
		return true;
	}

	@Override
	@Transactional
	public AddMoneyResponseModel addMoney(AddMoneyModel addMoney) {
		AddMoneyResponseModel addMoneyResponseModel = new AddMoneyResponseModel();
		//since wallet id is always same as of user id
		Optional<UserWallet> walletInfo = userWalletRepositiory.findById(addMoney.getUserId());
		if (walletInfo.isPresent()) {
			UserWallet wallet = walletInfo.get();
			if ((wallet.getBalance() + addMoney.getAmount()) >= wallet.getMaxBalanceLimit()) {
				addMoneyResponseModel.setSuccess(false);
				addMoneyResponseModel.setMessage("Wallet balance limit exceeded.");
			}
			else {
				wallet.setBalance(wallet.getBalance() + addMoney.getAmount());
				wallet.setUpdatedOn(new Date());
				updateTransaction(addMoney);
				addMoneyResponseModel.setSuccess(true);
				addMoneyResponseModel.setTotalBalance(wallet.getBalance());
				addMoneyResponseModel.setMessage("Balance updated Successfully..");
			}
		}
		else {
			addMoneyResponseModel.setSuccess(false);
			addMoneyResponseModel.setMessage("Wallet not found. Please contact admin.");
		}
		return addMoneyResponseModel;
	}

	private void updateTransaction(AddMoneyModel addMoney) {
		Transaction transaction = new Transaction();
		
		transaction.setComment("Add Money");
		transaction.setCreatedOn(new Date());
		transaction.setTransactionAmount(addMoney.getAmount());
		transaction.setTransactionRefNumber(UUID.randomUUID().toString());
		transaction.setTransactionType('C');
		transaction.setUserId(addMoney.getUserId());
		
		transactionRepository.save(transaction);
	}

	@Override
	public GetTransactionsResponseModel getTransactions(int userId, int pageNo, int pageSize) {
		GetTransactionsResponseModel getTransactionsResponseModel = new GetTransactionsResponseModel();
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("created_on").descending());
		List<Transaction> transactions = transactionRepository.findUserTransactions(userId, paging);
		if (transactions != null) {
			List<TransactionDetail> transactionList = new ArrayList<TransactionDetail>();
			getTransactionsResponseModel.setTransactions(transactionList);
			AtomicInteger counter = new AtomicInteger(1);
			transactions.forEach(txn -> {
				TransactionDetail txnDetail = new TransactionDetail();
				txnDetail.setKey(String.valueOf(counter.getAndIncrement()));
				txnDetail.setAmount(txn.getTransactionAmount());
				
				String partyName = fetchPartyName(txn, userId);
				txnDetail.setDetail(partyName);
				
//				String pattern = "MM/dd/yyyy HH:mm:ss";14-May-2020 16:00:08 IST
				String pattern = "dd-MMM-yyyy HH:mm:ss z";
				DateFormat df = new SimpleDateFormat(pattern);
				txnDetail.setDate(df.format(txn.getCreatedOn()));
				
				Character transactionType = txn.getMerchantId() == 0 ? txn.getTransactionType() : (
						txn.getUserId() == userId ? txn.getTransactionType() : (txn.getTransactionType() == 'C' ? 'D' : 'C'));
				txnDetail.setType(transactionType);
				
				transactionList.add(txnDetail);
			});
		}
		Optional<UserWallet> walletInfo = userWalletRepositiory.findById(userId);
		if (walletInfo.isPresent()) {
			getTransactionsResponseModel.setTotalBalance(walletInfo.get().getBalance());
		}
		
		return getTransactionsResponseModel;
	}

	private String fetchPartyName(Transaction txn, int id) {
		String partyName;
		if (txn.getMerchantId() == 0) {
			partyName = "Add Money";
		}
		else {
			int partyId;
			if (txn.getUserId() == id) {
				partyId = txn.getMerchantId();
			}
			else {
				partyId = txn.getUserId();
			}
			Optional<User> user = userRepositiory.findById(partyId);
			if (user.isPresent()) {
				partyName = user.get().getFirstName() + " " + user.get().getLastName();
			}
			else {
				partyName = txn.getComment();
			}
		} 
		return partyName;
	}

}
