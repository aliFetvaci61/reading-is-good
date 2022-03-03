package com.alifetvaci.ReadingIsGood.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document("logs")
public class Log {

	@Id
	@JsonIgnore
	private String id;

	private String customerId;

	private LogType logType;

	private String newValue;

	private String oldValue;

	private Date createdAt;

	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Log(String customerId, LogType logType, String newValue, String oldValue, Date createdAt) {
		super();
		this.customerId = customerId;
		this.logType = logType;
		this.newValue = newValue;
		this.oldValue = oldValue;
		this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
