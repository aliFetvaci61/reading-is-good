package com.alifetvaci.ReadingIsGood.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alifetvaci.ReadingIsGood.models.Log;
import com.alifetvaci.ReadingIsGood.models.LogType;
import com.alifetvaci.ReadingIsGood.repository.LogRepository;
import com.alifetvaci.ReadingIsGood.services.IAuthenticationFacadeService;
import com.alifetvaci.ReadingIsGood.services.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private IAuthenticationFacadeService iAuthenticationFacadeService;

	@Override
	@Async
	public void insertLog(LogType logType, String newValue, String oldValue) {
		Log log = new Log(iAuthenticationFacadeService.getAuthanticatedCustomerId(), logType, newValue, oldValue,
				new Date());
		logRepository.save(log);

	}

	@Override
	@Async
	public void insertLog(String customerId, LogType logType, String newValue, String oldValue) {
		Log log = new Log(customerId, logType, newValue, oldValue, new Date());
		logRepository.save(log);

	}

}
