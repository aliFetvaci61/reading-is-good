package com.alifetvaci.ReadingIsGood.security.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alifetvaci.ReadingIsGood.exception.BadRequestException;
import com.alifetvaci.ReadingIsGood.models.Customer;
import com.alifetvaci.ReadingIsGood.payload.request.LoginCustomer;
import com.alifetvaci.ReadingIsGood.payload.request.RegisterCustomer;
import com.alifetvaci.ReadingIsGood.payload.response.JwtResponse;
import com.alifetvaci.ReadingIsGood.repository.CustomerRepository;
import com.alifetvaci.ReadingIsGood.security.jwt.JwtUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Override
	public Customer registerCustomer(RegisterCustomer registerCustomer) {
		if (customerRepository.existsByEmail(registerCustomer.getEmail())) {
			logger.info("Email exist : " + registerCustomer.getEmail());
			throw new BadRequestException("Email exist : " + registerCustomer.getEmail());
		}

		Customer customer = new Customer();
		customer.setEmail(registerCustomer.getEmail());
		customer.setPassword(encoder.encode(registerCustomer.getPassword()));

		customer.setUsername(registerCustomer.getUsername());
		Customer savedCustomer = customerRepository.save(customer);
		logger.info("Registered new Customer");
		return savedCustomer;
	}

	@Override
	public JwtResponse loginCustomer(LoginCustomer login) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtTokenForCustomer(authentication);
		logger.info("Login Customer");
		return new JwtResponse(jwt);
	}

}
