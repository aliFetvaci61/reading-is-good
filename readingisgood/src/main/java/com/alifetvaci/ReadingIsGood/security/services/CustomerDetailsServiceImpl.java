package com.alifetvaci.ReadingIsGood.security.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsServiceImpl  implements CustomerDetailsService{

	@Override
	public String getAuthanticationCustomerId(Authentication authentication) {
		CustomerDetailsImpl customerDetailsImpl = (CustomerDetailsImpl) (authentication.getPrincipal());
		return customerDetailsImpl.getId();
	}

	@Override
	public CustomerServiceImpl getAuthanticatedCustomer(Authentication authentication) {
		CustomerServiceImpl userDetails = (CustomerServiceImpl) (authentication.getPrincipal());

		return userDetails;
	}

	@Override
	public boolean checkFraud(Authentication authentication, String userId) {
		CustomerDetailsImpl customerDetailsImpl = (CustomerDetailsImpl) (authentication.getPrincipal());
		if (!userId.equals(customerDetailsImpl.getId())) {
			return false;
		}
		return true;
	}

	

	
}
