package com.ardikars.jxpcap.util;

public class JxpcapException extends Exception {

	private static final long serialVersionUID = 1L;

	public JxpcapException() {
		super();
	}
	public JxpcapException(String message) {
		super(message);
	}
	public JxpcapException(String message, Throwable cause) {
        super(message, cause);
    }
	public JxpcapException(Throwable cause) {
        super(cause);
    }
}