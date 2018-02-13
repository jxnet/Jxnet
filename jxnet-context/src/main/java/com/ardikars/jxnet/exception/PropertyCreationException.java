package com.ardikars.jxnet.exception;

public class PropertyCreationException extends RuntimeException {

	public PropertyCreationException() {
		super();
	}

	public PropertyCreationException(String message) {
		super(message);
	}

	public PropertyCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyCreationException(Throwable cause) {
		super(cause);
	}

	protected PropertyCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
