package com.ardikars.jxnet;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.4
 */
public enum RadioFrequencyMonitorMode {

    RFMON(1), NON_RFMON(0);

    private final int value;

    private RadioFrequencyMonitorMode(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
