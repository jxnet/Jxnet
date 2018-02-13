package com.ardikars.jxnet.exception;

public class DuplicatePropertyOrderException extends RuntimeException {

	public DuplicatePropertyOrderException() {
		super();
	}

	public DuplicatePropertyOrderException(String message) {
		super(message);
	}

	public DuplicatePropertyOrderException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatePropertyOrderException(Throwable cause) {
		super(cause);
	}

	protected DuplicatePropertyOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
