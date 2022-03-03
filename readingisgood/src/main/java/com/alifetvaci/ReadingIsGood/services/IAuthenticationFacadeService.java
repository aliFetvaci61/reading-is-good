package com.alifetvaci.ReadingIsGood.services;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacadeService {
	  Authentication getAuthentication();
	  String getAuthanticatedCustomerId();
}
