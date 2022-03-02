package com.alifetvaci.ReadingIsGood.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alifetvaci.ReadingIsGood.models.Customer;
import com.alifetvaci.ReadingIsGood.repository.CustomerRepository;



@Service
public class CustomerServiceImpl implements UserDetailsService{

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return CustomerDetailsImpl.build(customer);
	}

	
		
}
