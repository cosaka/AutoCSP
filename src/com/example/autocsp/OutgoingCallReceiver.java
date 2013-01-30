package com.example.autocsp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OutgoingCallReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (SettingsActivity.getActive() == false) {
			return;
		}
		
		// Try to read the phone number from previous receivers.
		String phoneNumber = getResultData();
		
		if (shouldCancel(phoneNumber)) {
			return;
		}
		
		if (phoneNumber == null) {
			// Previous result not found. Use the original phone number in this case.
			phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
		}
		
		if (shouldCancel(phoneNumber)) {
			// Cancel our call.
			setResultData(null);
		} else {
			// Use rewritten number as the result data.
			setResultData(reformatNumber(phoneNumber));
		}
	}
	
	public boolean shouldCancel(String phoneNumber)
	{
		if (phoneNumber.contains("*") || phoneNumber.contains("#"))
			return true;
		
		if (phoneNumber.startsWith("00"))
			return true;
		
		if (phoneNumber.length() > 14)
			return true;
		
		return false;
	}
	
	public String reformatNumber(String phoneNumber)
	{
		if (SettingsActivity.getCSP().compareTo("") !=0) {
			// Removes "+" symbols
			while (phoneNumber.startsWith("+")) {
				phoneNumber = phoneNumber.substring(1, (phoneNumber.length()));
			}
			
			// Removes "0" digits
			while (phoneNumber.startsWith("0")) {
				phoneNumber = phoneNumber.substring(1, (phoneNumber.length()));
			}
			
			if (phoneNumber.length() > (13)) {
				// Removes country code
				phoneNumber = phoneNumber.substring(2, (phoneNumber.length()));
			}
			
			if (phoneNumber.length() > (11)) {
				// Removes country code or CSP code
				phoneNumber = phoneNumber.substring(2, (phoneNumber.length()));
			}
			
			if (phoneNumber.length() > (9)) {
				// It's a call with area code
				if (SettingsActivity.getLocalCall() == true) {
					if (phoneNumber.startsWith(SettingsActivity.getDDD())) {
						// Local call, then removes the area code
						return phoneNumber.substring(2, (phoneNumber.length()));
					}
				}
				
				// Inserts CSP code
				return "0" + SettingsActivity.getCSP() + phoneNumber;
			}
		}
		
		return phoneNumber;
	}
}