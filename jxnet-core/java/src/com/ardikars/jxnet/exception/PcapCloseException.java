
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.exception;

public final class PcapCloseException extends Exception {

    private static final long serialVersionUID = 1L;

    public PcapCloseException() {
        super();
    }

    public PcapCloseException(String message) {
        super(message);
    }

    public PcapCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PcapCloseException(Throwable cause) {
        super(cause);
    }

}
