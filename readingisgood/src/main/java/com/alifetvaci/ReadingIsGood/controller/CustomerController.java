package com.alifetvaci.ReadingIsGood.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.ReadingIsGood.models.Customer;
import com.alifetvaci.ReadingIsGood.payload.request.LoginCustomer;
import com.alifetvaci.ReadingIsGood.payload.request.RegisterCustomer;
import com.alifetvaci.ReadingIsGood.payload.response.JwtResponse;
import com.alifetvaci.ReadingIsGood.repository.CustomerRepository;
import com.alifetvaci.ReadingIsGood.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
		
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/auth/register")
	public ResponseEntity<Customer> registerCustomer(@RequestBody RegisterCustomer registerCustomer) {
		
		if (customerRepository.existsByEmail(registerCustomer.getEmail())) {
			return ResponseEntity.badRequest().build();
		}
		
		Customer customer = new Customer();
		customer.setEmail(registerCustomer.getEmail());
		customer.setPassword(encoder.encode(registerCustomer.getPassword()));

		customer.setUsername(registerCustomer.getUsername());
		Customer savedCustomer = customerRepository.save(customer);
		logger.info("Registered new Customer");
		return ResponseEntity.ok(savedCustomer);

	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<JwtResponse> loginCustomer(@RequestBody LoginCustomer login) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtTokenForCustomer(authentication);
		logger.info("Login Customer");
		return ResponseEntity.ok(new JwtResponse(jwt));

	}

}
