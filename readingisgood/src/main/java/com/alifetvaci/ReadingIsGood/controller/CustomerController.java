package com.alifetvaci.ReadingIsGood.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.ReadingIsGood.models.Customer;
import com.alifetvaci.ReadingIsGood.payload.request.LoginCustomer;
import com.alifetvaci.ReadingIsGood.payload.request.RegisterCustomer;
import com.alifetvaci.ReadingIsGood.payload.response.JwtResponse;
import com.alifetvaci.ReadingIsGood.services.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/auth/register")
	public ResponseEntity<Customer> registerCustomer(@RequestBody @Valid RegisterCustomer registerCustomer) {

		return ResponseEntity.ok(customerService.registerCustomer(registerCustomer));

	}

	@PostMapping("/auth/login")
	public ResponseEntity<JwtResponse> loginCustomer(@RequestBody @Valid LoginCustomer login) {

		return ResponseEntity.ok(customerService.loginCustomer(login));

	}

}
