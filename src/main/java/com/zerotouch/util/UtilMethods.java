package com.zerotouch.util;

public class UtilMethods {

	public static boolean validateMobileNo(long mobileNo) {
		try {
			int noOfDigits = 0;
			while(mobileNo != 0) {
				mobileNo /= 10;
				noOfDigits++;
			}
			if(noOfDigits == 10) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
