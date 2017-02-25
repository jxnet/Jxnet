package com.ardikars.jxnet.exception;

public class NotValidObjectException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotValidObjectException() {
        super();
    }

    public NotValidObjectException(String message) {
        super(message);
    }

    public NotValidObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidObjectException(Throwable cause) {
        super(cause);
    }

}
