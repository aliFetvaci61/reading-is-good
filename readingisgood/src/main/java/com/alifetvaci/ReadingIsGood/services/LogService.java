package com.alifetvaci.ReadingIsGood.services;

import com.alifetvaci.ReadingIsGood.models.LogType;

public interface LogService {
	
	void insertLog(LogType logType,String newValue,String oldValue);
	void insertLog(String customerId,LogType logType,String newValue,String oldValue);

}
