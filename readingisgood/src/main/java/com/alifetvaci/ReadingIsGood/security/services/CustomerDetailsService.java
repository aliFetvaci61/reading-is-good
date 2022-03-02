package com.alifetvaci.ReadingIsGood.security.services;

import org.springframework.security.core.Authentication;

public interface CustomerDetailsService {
	
	String getAuthanticationCustomerId(Authentication authentication);

	CustomerServiceImpl getAuthanticatedCustomer(Authentication authentication);

	boolean checkFraud(Authentication authentication, String userId);

}
