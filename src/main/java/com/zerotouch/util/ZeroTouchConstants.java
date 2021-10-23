package com.zerotouch.util;

import java.util.Date;

public class ZeroTouchConstants {

	public static final float USER_SWALLET_MIN_BAL = 0.00f;
	public static final float USER_SWALLET_MAX_BAL = 100000.00f;
	
	public static final Date USER_EXPIRY_DATE = new Date(2030,12,12);
	
	public static final String USER_STATUS_ACTIVE = "Active";
	
	public static final String RESPONSE_CODE_OK = "200";
	public static final String RESPONSE_MESSAGE_OK = "OK";
	
	public static final String RESPONSE_CODE_DATA_NOT_FOUND = "404";
	public static final String RESPONSE_MESSAGE_DATA_NOT_FOUND = "Data_Not_Found";
	
	public static final String RESPONSE_CODE_INVALID_REQUEST = "1001";
	public static final String RESPONSE_MESSAGE_INVALID_REQUEST = "Invalid Request";
	
	public static final String RESPONSE_CODE_DATA_ALREADY_EXISTS = "1002";
	public static final String RESPONSE_MESSAGE_DATA_ALREADY_EXISTS = "Data already exits";
	
	public static final String RESPONSE_CODE_INCORRECT_PASSWORD = "1003";
	public static final String RESPONSE_MESSAGE_INCORRECT_PASSWORD = "Password Incorrect";
	
	public static final String RESPONSE_CODE_INSUFFICIENT_BALANCE = "1004";
	public static final String RESPONSE_MESSAGE_INSUFFICIENT_BALANCE = "Insufficient Balance";
}
