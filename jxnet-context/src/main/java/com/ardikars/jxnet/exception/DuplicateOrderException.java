package com.ardikars.jxnet.exception;

public class DuplicateOrderException extends RuntimeException {

	public DuplicateOrderException() {
		super();
	}

	public DuplicateOrderException(String message) {
		super(message);
	}

	public DuplicateOrderException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateOrderException(Throwable cause) {
		super(cause);
	}

	protected DuplicateOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
