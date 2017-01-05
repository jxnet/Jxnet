
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.exception;

public final class BpfProgramCloseException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public BpfProgramCloseException() {
		super();
	}
	
	public BpfProgramCloseException(String message) {
		super(message);
	}
	
	public BpfProgramCloseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BpfProgramCloseException(Throwable cause) {
		super(cause);
	}
	
}
