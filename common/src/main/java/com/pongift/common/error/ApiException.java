package com.pongift.common.error;

import org.springframework.http.HttpStatus;

public class ApiException extends AbstractException {
	public ApiException(boolean success, String message) {
		super(success, message);
	}

	public ApiException(boolean success, String message, Throwable err) {
		super(success, message, err);
	}

	public ApiException(boolean success, HttpStatus httpStatus) {
		super(success, String.valueOf(httpStatus));
	}
}
