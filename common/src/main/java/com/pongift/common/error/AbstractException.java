package com.pongift.common.error;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Abstract bot exception.
 */
@Getter
@Setter
public class AbstractException extends RuntimeException {
	private boolean success;
	private String message;

	private AbstractException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * Instantiates a new Abstract bot exception.
	 *
	 * @param success the true/false
	 * @param message the message
	 */
	protected AbstractException(boolean success, String message) {
		this(message);
		this.success = success;
	}

	private AbstractException(String message, Throwable err) {
		super(message, err);
		this.message = message;
	}

	/**
	 * Instantiates a new Abstract bot exception.
	 *
	 * @param success the true/false
	 * @param message the message
	 * @param err the err
	 */
	protected AbstractException(boolean success, String message, Throwable err) {
		this(message, err);
		this.success = success;
	}

}
