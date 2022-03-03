package com.alifetvaci.ReadingIsGood.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alifetvaci.ReadingIsGood.models.Customer;
import com.alifetvaci.ReadingIsGood.repository.CustomerRepository;
import com.alifetvaci.ReadingIsGood.services.IAuthenticationFacadeService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService , IAuthenticationFacadeService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return CustomerDetailsImpl.build(customer);
	}

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public String getAuthanticatedCustomerId() {
		CustomerDetailsImpl customerDetailsImpl = (CustomerDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return customerDetailsImpl.getId();
	}
	
	

}
