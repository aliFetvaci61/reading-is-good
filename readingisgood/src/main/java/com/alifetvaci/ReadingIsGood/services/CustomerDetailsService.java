package com.alifetvaci.ReadingIsGood.services;

import org.springframework.security.core.Authentication;

import com.alifetvaci.ReadingIsGood.services.impl.CustomerServiceImpl;

public interface CustomerDetailsService {
	
	String getAuthanticationCustomerId(Authentication authentication);

	CustomerServiceImpl getAuthanticatedCustomer(Authentication authentication);

	boolean checkFraud(Authentication authentication, String userId);

}
