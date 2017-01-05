
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.exception;

public final class JxnetException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public JxnetException() {
		super();
	}
	
	public JxnetException(String message) {
		super(message);
	}
	
	public JxnetException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public JxnetException(Throwable cause) {
		super(cause);
	}
	
}
