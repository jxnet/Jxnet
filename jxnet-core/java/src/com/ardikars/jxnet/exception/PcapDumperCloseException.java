
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.exception;

public final class PcapDumperCloseException extends Exception {

    private static final long serialVersionUID = 1L;

    public PcapDumperCloseException() {
            super();
    }

    public PcapDumperCloseException(String message) {
            super(message);
    }
    
    public PcapDumperCloseException(String message, Throwable cause) {
    	super(message, cause);
    }
    
    public PcapDumperCloseException(Throwable cause) {
    	super(cause);
    }

}
