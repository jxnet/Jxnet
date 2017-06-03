package com.ardikars.jxnet;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.4
 */
public enum ImmediateMode {

    IMMEDIATE(1), NON_IMMEDIATE(0);

    private final int value;

    private ImmediateMode(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
