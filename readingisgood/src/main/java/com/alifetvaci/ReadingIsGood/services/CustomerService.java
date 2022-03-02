package com.alifetvaci.ReadingIsGood.services;

import com.alifetvaci.ReadingIsGood.models.Customer;
import com.alifetvaci.ReadingIsGood.payload.request.LoginCustomer;
import com.alifetvaci.ReadingIsGood.payload.request.RegisterCustomer;
import com.alifetvaci.ReadingIsGood.payload.response.JwtResponse;

public interface CustomerService {
	
	Customer registerCustomer(RegisterCustomer registerCustomer);
	JwtResponse loginCustomer(LoginCustomer login);

}
