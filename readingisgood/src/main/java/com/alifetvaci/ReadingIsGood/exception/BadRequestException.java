package com.alifetvaci.ReadingIsGood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadRequestException extends RuntimeException {
	public BadRequestException(String exception) {
		super(exception);
	}

}
